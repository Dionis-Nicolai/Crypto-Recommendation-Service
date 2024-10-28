package org.faptic.crypto.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

import static org.faptic.crypto.data.Constant.CSV_SUFFIX;
import static org.faptic.crypto.data.Constant.TEMP_FILE_NAME;

@Service
public class CsvFileReader implements FileReader {

    @Override
    public String getFileNameBySymbol(String cryptoSymbol) {
        return "/crypto-data/" + cryptoSymbol + "_values.csv";
    }

    @Override
    public List<String> readFile(String cryptoSymbol) {
        List<String> lines;
        try (InputStream inputStream = getClass().getResourceAsStream(getFileNameBySymbol(cryptoSymbol))) {
            if (inputStream == null) {
                return List.of();
            }

            Path tempFile = Files.createTempFile(TEMP_FILE_NAME, CSV_SUFFIX);
            Files.copy(inputStream, tempFile, StandardCopyOption.REPLACE_EXISTING);

            lines = Files.readAllLines(tempFile);

            Files.delete(tempFile);

            return lines;
        } catch (IOException e) {
            return List.of();
        }
    }
}
