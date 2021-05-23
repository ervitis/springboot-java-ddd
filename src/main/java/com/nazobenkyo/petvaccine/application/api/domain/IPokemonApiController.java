package com.nazobenkyo.petvaccine.application.api.domain;

import com.nazobenkyo.petvaccine.domain.repository.model.Pokemon;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
public interface IPokemonApiController {

    @GetMapping(value = "/{entity}", produces = MediaType.APPLICATION_JSON_VALUE)
    Pokemon getPokemon(@PathVariable String entity);
}
