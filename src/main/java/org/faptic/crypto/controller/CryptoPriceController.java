package org.faptic.crypto.controller;

import lombok.RequiredArgsConstructor;
import org.faptic.crypto.data.CryptoDetails;
import org.faptic.crypto.data.CryptoStatistic;
import org.faptic.crypto.data.CryptoSymbol;
import org.faptic.crypto.data.CryptoWithNormalizedRange;
import org.faptic.crypto.data.Frequency;
import org.faptic.crypto.service.RecommendationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CryptoPriceController {

    private final RecommendationService recommendationService;

    @GetMapping("/prices")
    public List<Double> getAllCryptoPrices() {
        return recommendationService.getAllPrices();
    }

    @GetMapping("/statistics")
    public List<CryptoStatistic> getAllStatisticsPerMonth() {
        return recommendationService.calculateStatisticForEachSymbol();
    }

    @GetMapping("/statistics/{cryptoSymbol}")
    public CryptoStatistic getStatisticsForCryptoPerMonth(@PathVariable("cryptoSymbol") String cryptoSymbol) {
        return recommendationService.getCryptoStatisticsBySymbol(CryptoSymbol.fromValue(cryptoSymbol));
    }

    @GetMapping("/recommendations/by-normalized-range/{frequency}")
    public List<CryptoWithNormalizedRange> getNormalizedRanges(@PathVariable("frequency") Frequency frequency,
                                                               @RequestParam(required = false) String order,
                                                               @RequestParam(required = false) LocalDate searchedDay) {
        return recommendationService.getCryptosByNormalizedRange(frequency, order, searchedDay);
    }

    @GetMapping("/recommendations/highest-normalized-range/{frequency}")
    public CryptoWithNormalizedRange getHighestNormalizedRange(@PathVariable("frequency") Frequency frequency,
                                                               @RequestParam LocalDate searchedDay) {
        return recommendationService.getHighestNormalizedRangeFor(frequency, searchedDay);
    }

    @GetMapping("/recommendations/{cryptoSymbol}")
    public List<CryptoDetails> getCryptoRecommendationsBySymbol(@PathVariable("cryptoSymbol") String cryptoSymbol) {
        return recommendationService.getRecommendations(cryptoSymbol, Frequency.PER_MONTH, LocalDate.now());
    }
}
