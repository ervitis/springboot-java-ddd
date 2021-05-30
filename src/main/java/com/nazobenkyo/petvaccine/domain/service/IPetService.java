package com.nazobenkyo.petvaccine.domain.service;

import com.nazobenkyo.petvaccine.model.Pet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IPetService {
    Pet create(Pet pet);

    Pet get(String emailOwner);

    Pet update(Pet pet);

    void delete(String id);

    Page<Pet> getAll(Pageable pageable);
}
