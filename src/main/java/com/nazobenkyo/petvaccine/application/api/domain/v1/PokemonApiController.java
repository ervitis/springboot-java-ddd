package com.nazobenkyo.petvaccine.application.api.domain.v1;

import com.nazobenkyo.petvaccine.application.api.domain.IPokemonApiController;
import com.nazobenkyo.petvaccine.domain.repository.model.Pokemon;
import com.nazobenkyo.petvaccine.domain.service.IPokemonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@Api(value = "Pokemon controller", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PokemonApiController implements IPokemonApiController {

    private final IPokemonService pokemonService;

    public PokemonApiController(IPokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @Override
    @ApiOperation(value = "get a pokemon")
    @PreAuthorize("hasRole('USER')")
    public Pokemon getPokemon(String entity) {
        return this.pokemonService.getPokemon(entity);
    }
}
