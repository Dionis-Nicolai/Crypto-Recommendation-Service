package org.faptic.crypto.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.ZoneId;
import java.util.function.Function;

import static org.faptic.crypto.data.Constant.CSV_SEPARATOR;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Mapper {

    public static final Function<String, CryptoDetails> CSV_LINE_TO_CRYPTO_DETAILS =
        rowLine -> {
        String[] data = rowLine.split(CSV_SEPARATOR);

        return new CryptoDetails(Instant.ofEpochMilli(Long.parseLong(data[0]))
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime(), CryptoSymbol.fromValue(data[1]), Double.valueOf(data[2]));
    };
}
