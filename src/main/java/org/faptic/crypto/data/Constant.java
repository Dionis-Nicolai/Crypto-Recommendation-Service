package org.faptic.crypto.data;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class Constant {

    public static final String CSV_SEPARATOR = ",";
    public static final String CSV_SUFFIX = ".csv";
    public static final String TEMP_FILE_NAME = "temp-";
    public static final String ASCENDING = "ASCENDING";
    public static final String DESCENDING = "DESCENDING";
    public static final Long NUMBER_OF_CSV_HEADER_LINES = 1L;
}
