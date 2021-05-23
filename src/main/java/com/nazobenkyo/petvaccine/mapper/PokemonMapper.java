package com.nazobenkyo.petvaccine.mapper;

import com.nazobenkyo.petvaccine.domain.repository.model.Pokemon;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PokemonMapper {
    PokemonMapper INSTANCE = Mappers.getMapper(PokemonMapper.class);

    Pokemon pokemonRequestToPokemonDto(com.nazobenkyo.petvaccine.infra.requestclient.domain.model.Pokemon pokemon);
}
