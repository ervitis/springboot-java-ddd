package com.nazobenkyo.petvaccine.domain.repository;

import com.nazobenkyo.petvaccine.model.Pet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPetRepository extends MongoRepository<Pet, String> {
    Optional<Pet> findByOwnerEmail(String email);
}
