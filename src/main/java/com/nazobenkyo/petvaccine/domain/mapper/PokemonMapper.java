package com.nazobenkyo.petvaccine.domain.mapper;

import com.nazobenkyo.petvaccine.domain.repository.model.Pokemon;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PokemonMapper {
    Pokemon pokemonRequestToPokemonDto(com.nazobenkyo.petvaccine.infra.requestclient.domain.model.Pokemon pokemon);
}
