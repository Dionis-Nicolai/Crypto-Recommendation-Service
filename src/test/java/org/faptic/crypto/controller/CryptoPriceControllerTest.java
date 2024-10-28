package org.faptic.crypto.controller;

import org.faptic.crypto.data.CryptoSymbol;
import org.faptic.crypto.service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import static org.faptic.crypto.data.TestData.IN_MEMORY_CRYPTO_DETAILS;
import static org.faptic.crypto.data.TestData.IN_MEMORY_CRYPTO_PRICES;
import static org.faptic.crypto.data.TestData.IN_MEMORY_CRYPTO_STATISTICS;
import static org.faptic.crypto.data.TestData.IN_MEMORY_CRYPTO_WITH_NORMALIZED_RANGE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CryptoPriceController.class)
class CryptoPriceControllerTest extends BaseControllerUnitTest {

    @MockBean
    private RecommendationService recommendationService;

    @Test
    void testGetAllCryptoPrices() throws Exception {
        when(recommendationService.getAllPrices())
            .thenReturn(IN_MEMORY_CRYPTO_PRICES);

        ResultActions result = performGetRequest(API_BASE_PATH + "/prices");

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.[0]").value(1.0))
            .andExpect(jsonPath("$.[1]").value(2.0))
            .andExpect(jsonPath("$.[2]").value(3.0));
    }

    @Test
    void testGetAllCryptoStatistics() throws Exception {
        when(recommendationService.calculateStatisticForEachSymbol())
            .thenReturn(IN_MEMORY_CRYPTO_STATISTICS);

        ResultActions result = performGetRequest(API_BASE_PATH + "/statistics");

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].symbol").value(CryptoSymbol.BTC.name()))
            .andExpect(jsonPath("$.[0].oldest.symbol").value(CryptoSymbol.BTC.name()))
            .andExpect(jsonPath("$.[0].oldest.localDateTime")
                .value("-999999999-01-01T00:00:00"))
            .andExpect(jsonPath("$.[0].newest.localDateTime")
                .value("+999999999-12-31T23:59:59.999999999"))
            .andExpect(jsonPath("$.[0].minimum.price").value(1.0))
            .andExpect(jsonPath("$.[0].maximum.price").value(5.0))
            .andExpect(jsonPath("$.[1].oldest.symbol").value(CryptoSymbol.ETH.name()))
            .andExpect(jsonPath("$.[1].minimum.price").value(.1))
            .andExpect(jsonPath("$.[1].maximum.price").value(5.5));
    }

    @Test
    void testGetCryptoStatisticsBySymbol() throws Exception {
        when(recommendationService.getCryptoStatisticsBySymbol(any()))
            .thenReturn(IN_MEMORY_CRYPTO_STATISTICS.getFirst());

        ResultActions result = performGetRequestWithParameters(API_BASE_PATH + "/statistics/", CryptoSymbol.BTC);

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.symbol").value(CryptoSymbol.BTC.name()))
            .andExpect(jsonPath("$.oldest.symbol").value(CryptoSymbol.BTC.name()))
            .andExpect(jsonPath("$.oldest.localDateTime")
                .value("-999999999-01-01T00:00:00"))
            .andExpect(jsonPath("$.newest.localDateTime")
                .value("+999999999-12-31T23:59:59.999999999"))
            .andExpect(jsonPath("$.minimum.price").value(1.0))
            .andExpect(jsonPath("$.maximum.price").value(5.0));
    }

    @Test
    void testGetNormalizedRanges() throws Exception {
        when(recommendationService.getCryptosByNormalizedRange(any(), any(), any()))
            .thenReturn(IN_MEMORY_CRYPTO_WITH_NORMALIZED_RANGE);

        ResultActions result = performGetRequestWithParameters(API_BASE_PATH + "/recommendations/by-normalized-range/PER_MONTH");

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].cryptoName").value(CryptoSymbol.BTC.name()))
            .andExpect(jsonPath("$.[0].normalizedRange").value(0.15))
            .andExpect(jsonPath("$.[1].cryptoName").value(CryptoSymbol.ETH.name()))
            .andExpect(jsonPath("$.[1].normalizedRange").value(0.20));
    }

    @Test
    void testGetHighestNormalizedRange() throws Exception {
        when(recommendationService.getHighestNormalizedRangeFor(any(), any()))
            .thenReturn(IN_MEMORY_CRYPTO_WITH_NORMALIZED_RANGE.getFirst());

        ResultActions result = performGetRequestWithParameters(API_BASE_PATH +
            "/recommendations/highest-normalized-range/PER_MONTH" + "?searchedDay=2022-01-03");

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.cryptoName").value(CryptoSymbol.BTC.name()))
            .andExpect(jsonPath("$.normalizedRange").value(0.15));
    }

    @Test
    void testGetCryptoRecommendationsBySymbol() throws Exception {
        when(recommendationService.getRecommendations(any(), any(), any()))
            .thenReturn(IN_MEMORY_CRYPTO_DETAILS);

        ResultActions result = performGetRequestWithParameters(API_BASE_PATH + "/recommendations/", CryptoSymbol.BTC);

        result.andExpect(status().isOk())
            .andExpect(jsonPath("$.[0].price").value(10.0))
            .andExpect(jsonPath("$.[1].price").value(20.0))
            .andExpect(jsonPath("$.[2].price").value(30.0));
    }
}