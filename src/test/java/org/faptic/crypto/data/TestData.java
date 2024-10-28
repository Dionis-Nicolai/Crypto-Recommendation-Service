package org.faptic.crypto.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class TestData {

    public static final List<Double> IN_MEMORY_CRYPTO_PRICES = List.of(1.0, 2.0, 3.0);

    public static final List<CryptoDetails> IN_MEMORY_CRYPTO_DETAILS =
        List.of(new CryptoDetails(LocalDateTime.now(), CryptoSymbol.BTC, 10.0),
            new CryptoDetails(LocalDateTime.now().minusHours(1), CryptoSymbol.BTC, 20.0),
            new CryptoDetails(LocalDateTime.now().minusHours(2), CryptoSymbol.BTC, 30.0));

    public static final List<CryptoStatistic> IN_MEMORY_CRYPTO_STATISTICS = List.of(
        new CryptoStatistic(CryptoSymbol.BTC,
            new CryptoDetails(LocalDateTime.MIN, CryptoSymbol.BTC, 2.0),
            new CryptoDetails(LocalDateTime.MAX, CryptoSymbol.BTC, 3.0),
            new CryptoDetails(LocalDateTime.MIN, CryptoSymbol.BTC, 1.0),
            new CryptoDetails(LocalDateTime.MIN, CryptoSymbol.BTC, 5.0)),
        new CryptoStatistic(CryptoSymbol.ETH,
            new CryptoDetails(LocalDateTime.MIN, CryptoSymbol.ETH, 1.0),
            new CryptoDetails(LocalDateTime.MAX, CryptoSymbol.ETH, 4.0),
            new CryptoDetails(LocalDateTime.MIN, CryptoSymbol.ETH, .1),
            new CryptoDetails(LocalDateTime.MIN, CryptoSymbol.ETH, 5.5)));

    public static final List<CryptoWithNormalizedRange> IN_MEMORY_CRYPTO_WITH_NORMALIZED_RANGE = List.of(
        new CryptoWithNormalizedRange("BTC", 0.15),
        new CryptoWithNormalizedRange("ETH", 0.20));
}
