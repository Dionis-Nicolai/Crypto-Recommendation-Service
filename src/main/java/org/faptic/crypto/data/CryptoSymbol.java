package org.faptic.crypto.data;

import lombok.RequiredArgsConstructor;
import org.faptic.crypto.exception.InvalidCryptoSymbolException;

import java.util.Arrays;

@RequiredArgsConstructor
public enum CryptoSymbol {

    BTC("BTC"),
    DOGE("DOGE"),
    ETH("ETH"),
    LTC("LTC"),
    XRP("XRP");

    private final String symbol;

    public static CryptoSymbol fromValue(String symbol) {
        return Arrays.stream(values())
            .filter(crypto -> symbol.equals(crypto.symbol))
            .findFirst()
            .orElseThrow(() -> new InvalidCryptoSymbolException("Invalid crypto symbol: " + symbol));
    }
}
