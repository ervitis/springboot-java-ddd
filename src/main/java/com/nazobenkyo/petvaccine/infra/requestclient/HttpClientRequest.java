package com.nazobenkyo.petvaccine.infra.requestclient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.util.Map;

public abstract class HttpClientRequest {
    private final WebClient.Builder webClientBuilder;
    private HttpClient httpClient;

    public HttpClientRequest(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder.clientConnector(this.clientHttpConnector());
    }

    private ReactorClientHttpConnector clientHttpConnector() {
        return new ReactorClientHttpConnector(this.httpClient());
    }

    private HttpClient httpClient() {
        this.httpClient = HttpClient.create(
                ConnectionProvider.builder("clientRequest")
                        .metrics(true)
                        .build()
        ).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 7000)
        .doOnConnected(conn -> conn
                .addHandlerLast(new ReadTimeoutHandler(10))
                .addHandlerLast(new WriteTimeoutHandler(10))
        );
        return this.httpClient;
    }

    public void onPort(int port) {
        this.httpClient.port(port);
    }

    public void headers(Map<String, Object> customHeaders) {
        customHeaders.forEach((k, v) -> this.httpClient.headers(h -> h.set(k, v)));
    }

    public void onBaseUrl(String baseUrl) {
        this.webClientBuilder.baseUrl(baseUrl);
    }

    public WebClient buildClient() {
        return this.webClientBuilder.build();
    }
}
