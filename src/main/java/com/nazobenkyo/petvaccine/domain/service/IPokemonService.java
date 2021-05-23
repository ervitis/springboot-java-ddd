package com.nazobenkyo.petvaccine.domain.service;

import com.nazobenkyo.petvaccine.domain.repository.model.Pokemon;

public interface IPokemonService {
    public Pokemon getPokemon(String entityId);
}
