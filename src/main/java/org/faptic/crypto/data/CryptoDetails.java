package org.faptic.crypto.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public record CryptoDetails(LocalDateTime localDateTime,
                            CryptoSymbol symbol,
                            Double price) implements Serializable {

    public static CryptoDetails noDetails(CryptoSymbol symbol) {
        return new CryptoDetails(null, symbol, 0.0);
    }

    public static CryptoDetails noDetails() {
        return noDetails(null);
    }
}
