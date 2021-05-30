package com.nazobenkyo.petvaccine.domain.service.impl;

import com.nazobenkyo.petvaccine.application.api.exception.model.NotFoundException;
import com.nazobenkyo.petvaccine.domain.repository.IPetRepository;
import com.nazobenkyo.petvaccine.domain.service.IPetService;
import com.nazobenkyo.petvaccine.model.Pet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PetService implements IPetService {
    @Autowired
    private IPetRepository petRepository;

    @Override
    public Pet create(Pet pet) {
        return this.petRepository.findByOwnerEmail(pet.getOwnerEmail()).orElseGet(() -> this.petRepository.save(pet));
    }

    @Override
    public Pet get(String emailOwner) {
        return this.petRepository.findByOwnerEmail(emailOwner).orElseThrow(NotFoundException::new);
    }

    @Override
    public Pet update(Pet pet) {
        Pet entity = this.petRepository.findByOwnerEmail(pet.getOwnerEmail()).orElseThrow(NotFoundException::new);
        entity.setName(pet.getName());
        entity.setType(pet.getType());
        entity.setOwnerEmail(pet.getOwnerEmail());
        return this.petRepository.save(entity);
    }

    @Override
    public void delete(String id) {
        Pet pet = this.petRepository.findById(id).orElseThrow(NotFoundException::new);
        this.petRepository.delete(pet);
    }

    @Override
    public Page<Pet> getAll(Pageable pageable) {
        return this.petRepository.findAll(pageable);
    }
}
