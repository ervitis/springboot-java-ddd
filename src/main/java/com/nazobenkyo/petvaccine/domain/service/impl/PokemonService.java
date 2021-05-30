package com.nazobenkyo.petvaccine.domain.service.impl;

import com.nazobenkyo.petvaccine.domain.repository.model.Pokemon;
import com.nazobenkyo.petvaccine.domain.service.IPokemonService;
import com.nazobenkyo.petvaccine.infra.requestclient.PokemonClientRequest;
import com.nazobenkyo.petvaccine.domain.mapper.PokemonMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
public class PokemonService implements IPokemonService {
    private final PokemonClientRequest httpClientRequest;

    private final PokemonMapper pokemonMapper;

    public PokemonService(PokemonClientRequest httpClientRequest) {
        this.httpClientRequest = httpClientRequest;
        this.pokemonMapper = Mappers.getMapper(PokemonMapper.class);
    }

    @Override
    public Pokemon getPokemon(String entityId) {
        return this.pokemonMapper.pokemonRequestToPokemonDto(this.httpClientRequest.getPokemon(entityId));
    }
}
