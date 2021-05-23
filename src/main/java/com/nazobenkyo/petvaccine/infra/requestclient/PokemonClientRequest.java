package com.nazobenkyo.petvaccine.infra.requestclient;

import com.nazobenkyo.petvaccine.infra.requestclient.domain.model.Pokemon;
import io.netty.handler.timeout.TimeoutException;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.Map;

@Repository
public class PokemonClientRequest extends HttpClientRequest {
    private final WebClient client;

    public PokemonClientRequest(WebClient.Builder webClientBuilder) {
        super(webClientBuilder);
        this.onPort(80);
        this.headers(Map.ofEntries(
                Map.entry("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                Map.entry("Accept", MediaType.APPLICATION_JSON_VALUE)
        ));
        this.onBaseUrl("https://pokeapi.co/api/v2");
        this.client = this.buildClient();
    }

    public Pokemon getPokemon(String entity) {
        return this.client.get().uri(uriBuilder -> uriBuilder.path("/pokemon/{entity}").build(entity))
                .retrieve()
                .bodyToMono(Pokemon.class)
                .retryWhen(Retry.backoff(2, Duration.ofMillis(100)).filter(throwable -> throwable instanceof TimeoutException))
                .block();
    }
}
