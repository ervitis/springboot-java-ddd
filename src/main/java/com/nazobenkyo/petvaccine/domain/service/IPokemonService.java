package com.nazobenkyo.petvaccine.domain.service;

import com.nazobenkyo.petvaccine.domain.repository.model.Pokemon;

public interface IPokemonService {
    Pokemon getPokemon(String entityId);
}
