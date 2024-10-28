package org.faptic.crypto.service;

import java.io.IOException;
import java.util.List;

public interface FileReader {

    String getFileNameBySymbol(String cryptoSymbol) throws IOException;

    List<String> readFile(String cryptoSymbol);
}
