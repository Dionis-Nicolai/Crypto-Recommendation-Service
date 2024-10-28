package org.faptic.crypto.service;

import org.faptic.crypto.data.CryptoSymbol;
import org.faptic.crypto.repository.CryptoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.faptic.crypto.data.TestData.IN_MEMORY_CRYPTO_DETAILS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class RecommendationServiceTest {

    @Mock
    private CryptoRepository cryptoRepository;

    @InjectMocks
    private RecommendationService recommendationService;

    @Test
    void getRecommendations() {
    }

    @Test
    void testGetAllPricesForBtcNotEmptyListResult() {
        doReturn(IN_MEMORY_CRYPTO_DETAILS)
            .when(cryptoRepository).findAllBySymbol(CryptoSymbol.BTC.name());

        List<Double> expectedPrices = List.of(10.0, 20.0, 30.0);
        List<Double> actualPrices = recommendationService.getAllPrices();

        assertEquals(expectedPrices, actualPrices);
    }

    @Test
    void testGetNoPricesForEmptyCryptoDetailsList() {
        doReturn(List.of()).when(cryptoRepository).findAllBySymbol(any());

        List<Double> actualPrices = recommendationService.getAllPrices();

        assertTrue(actualPrices.isEmpty());
    }
}