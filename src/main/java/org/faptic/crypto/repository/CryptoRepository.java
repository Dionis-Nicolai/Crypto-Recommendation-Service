package org.faptic.crypto.repository;

import org.faptic.crypto.data.CryptoDetails;

import java.util.List;

public interface CryptoRepository {

    List<CryptoDetails> findAllBySymbol(String cryptoSymbol);
}
