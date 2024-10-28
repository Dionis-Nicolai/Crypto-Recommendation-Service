package org.faptic.crypto.service;

import lombok.RequiredArgsConstructor;
import org.faptic.crypto.data.CryptoDetails;
import org.faptic.crypto.data.CryptoStatistic;
import org.faptic.crypto.data.CryptoSymbol;
import org.faptic.crypto.data.CryptoWithNormalizedRange;
import org.faptic.crypto.data.Frequency;
import org.faptic.crypto.data.Order;
import org.faptic.crypto.repository.CryptoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.Comparator.comparing;
import static org.faptic.crypto.data.Constant.DESCENDING;

@Service
@RequiredArgsConstructor
public class RecommendationService {

    private final CryptoRepository cryptoRepository;

    public List<Double> getAllPrices() {
        return Arrays.stream(CryptoSymbol.values()).parallel()
            .flatMap(cryptoSymbol ->
                getRecommendations(cryptoSymbol.name(), Frequency.PER_MONTH, LocalDate.now()).parallelStream())
            .map(CryptoDetails::price)
            .toList();
    }

    public List<CryptoDetails> getRecommendations(String cryptoName, Frequency frequency, LocalDate searchedDay) {
        return switch (frequency) {
            case PER_MONTH -> cryptoRepository.findAllBySymbol(cryptoName);
            case PER_DAY -> cryptoRepository.findAllBySymbol(cryptoName).parallelStream()
                .filter(crypto -> searchedDay.getDayOfYear() == crypto.localDateTime().getDayOfYear())
                .toList();
            default -> throw new IllegalStateException("Unexpected frequency: " + frequency);
        };
    }

    public List<CryptoStatistic> calculateStatisticForEachSymbol() {
        return Arrays.stream(CryptoSymbol.values()).parallel()
            .map(this::getCryptoStatisticsBySymbol)
            .toList();
    }

    public List<CryptoWithNormalizedRange> getCryptosByNormalizedRange(Frequency frequency, String order, LocalDate searchedDay) {
        return Arrays.stream(CryptoSymbol.values())
            .map(symbol -> new CryptoWithNormalizedRange(symbol.name(),
                getNormalizedRangePerCrypto(
                    getDetailsPerSymbolComparing(symbol, frequency, searchedDay, comparing(CryptoDetails::price)),
                    getDetailsPerSymbolComparing(symbol, frequency, searchedDay, comparing(CryptoDetails::price).reversed()))))
            .sorted(getNormalizedRangeComparator(order))
            .toList();
    }

    public CryptoWithNormalizedRange getHighestNormalizedRangeFor(Frequency frequency, LocalDate searchedDay) {
        return getCryptosByNormalizedRange(frequency, DESCENDING, searchedDay).stream()
            .findFirst()
            .orElse(CryptoWithNormalizedRange.DEFAULT);
    }

    public CryptoStatistic getCryptoStatisticsBySymbol(CryptoSymbol symbol) {
        try (ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())) {
            Future<CryptoDetails> oldestCryptoDetails = executorService.submit(
                () -> getDetailsPerSymbolComparing(symbol, Frequency.PER_MONTH, LocalDate.now(), comparing(CryptoDetails::localDateTime)));
            Future<CryptoDetails> newestCryptoDetails = executorService.submit(
                () -> getDetailsPerSymbolComparing(symbol, Frequency.PER_MONTH, LocalDate.now(), comparing(CryptoDetails::localDateTime).reversed()));
            Future<CryptoDetails> lowestPriceCryptoDetails = executorService.submit(
                () -> getDetailsPerSymbolComparing(symbol, Frequency.PER_MONTH, LocalDate.now(), comparing(CryptoDetails::price)));
            Future<CryptoDetails> highestPriceCryptoDetails = executorService.submit(
                () -> getDetailsPerSymbolComparing(symbol, Frequency.PER_MONTH, LocalDate.now(), comparing(CryptoDetails::price).reversed()));

            return new CryptoStatistic(symbol, oldestCryptoDetails.get(), newestCryptoDetails.get(),
                lowestPriceCryptoDetails.get(), highestPriceCryptoDetails.get());
        } catch (ExecutionException | InterruptedException ex) {
            return CryptoStatistic.UNKNOWN;
        }
    }

    private Double getNormalizedRangePerCrypto(CryptoDetails detailsForMinimumPrice, CryptoDetails detailsForMaximumPrice) {
        Double minimumPrice = detailsForMinimumPrice.price();
        Double maximumPrice = detailsForMaximumPrice.price();

        return (maximumPrice - minimumPrice) / minimumPrice;
    }

    private CryptoDetails getDetailsPerSymbolComparing(CryptoSymbol symbol,
                                                       Frequency frequency, LocalDate searchedDay,
                                                       Comparator<CryptoDetails> comparator) {
        return getRecommendations(symbol.name(), frequency, searchedDay).stream()
            .min(comparator)
            .orElseGet(() -> CryptoDetails.noDetails(symbol));
    }

    private Comparator<CryptoWithNormalizedRange> getNormalizedRangeComparator(String order) {
        if (Order.ASCENDING.getAcceptedValues().contains(order)) {
            return comparing(CryptoWithNormalizedRange::normalizedRange);
        } else if (Order.DESCENDING.getAcceptedValues().contains(order)) {
            return comparing(CryptoWithNormalizedRange::normalizedRange).reversed();
        } else {
            return Comparator.naturalOrder();
        }
    }
}
