package org.faptic.crypto.data;

import lombok.NonNull;

import java.io.Serializable;

public record CryptoWithNormalizedRange(String cryptoName,
                                        double normalizedRange) implements Serializable, Comparable<CryptoWithNormalizedRange> {

    public static final CryptoWithNormalizedRange DEFAULT = new CryptoWithNormalizedRange(null,0.0);

    @Override
    public int compareTo(@NonNull CryptoWithNormalizedRange other) {
        return 0;
    }
}
