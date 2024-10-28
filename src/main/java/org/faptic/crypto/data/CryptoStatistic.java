package org.faptic.crypto.data;

import java.io.Serializable;

public record CryptoStatistic(CryptoSymbol symbol,
                              CryptoDetails oldest,
                              CryptoDetails newest,
                              CryptoDetails minimum,
                              CryptoDetails maximum) implements Serializable {

    public static final CryptoStatistic UNKNOWN = new CryptoStatistic(null,
        CryptoDetails.noDetails(), CryptoDetails.noDetails(), CryptoDetails.noDetails(), CryptoDetails.noDetails());
}
