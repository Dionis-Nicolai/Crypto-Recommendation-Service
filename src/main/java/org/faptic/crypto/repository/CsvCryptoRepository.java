package org.faptic.crypto.repository;

import lombok.RequiredArgsConstructor;
import org.faptic.crypto.data.CryptoDetails;
import org.faptic.crypto.service.FileReader;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.faptic.crypto.data.Constant.NUMBER_OF_CSV_HEADER_LINES;
import static org.faptic.crypto.data.Mapper.CSV_LINE_TO_CRYPTO_DETAILS;

@Repository
@RequiredArgsConstructor
public class CsvCryptoRepository implements CryptoRepository {

    private final FileReader csvFileReader;

    @Override
    public List<CryptoDetails> findAllBySymbol(String cryptoSymbol) {
        return csvFileReader.readFile(cryptoSymbol).stream()
            .skip(NUMBER_OF_CSV_HEADER_LINES)
            .map(CSV_LINE_TO_CRYPTO_DETAILS)
            .toList();
    }
}
