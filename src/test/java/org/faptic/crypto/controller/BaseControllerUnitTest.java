package org.faptic.crypto.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.net.InetSocketAddress;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public abstract class BaseControllerUnitTest {

    protected static final String SERVER_NAME = "crypto-recommendation";
    protected static final int SERVER_PORT = 1234;
    protected static final String API_BASE_PATH = "/api/";

    @Autowired
    private MockMvc mockMvc;

    protected ResultActions performGetRequest(String uri) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setHost(InetSocketAddress.createUnresolved(SERVER_NAME, SERVER_PORT));

        return mockMvc.perform(
            MockMvcRequestBuilders.get(uri)
                .headers(headers)
                .contentType(APPLICATION_JSON_VALUE)
                .accept(APPLICATION_JSON_VALUE)
        );
    }

    protected ResultActions performGetRequestWithParameters(String uri, Object... parameters) throws Exception {
        return performGetRequest(uriWithParameters(uri, parameters));
    }

    private String uriWithParameters(String resource, Object... parameters) {
        StringBuilder uri = new StringBuilder(resource);
        for (Object param : parameters) {
            uri.append("/").append(param);
        }

        return uri.toString();
    }
}
