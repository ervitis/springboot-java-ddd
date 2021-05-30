package com.nazobenkyo.petvaccine.domain.repository;

import com.nazobenkyo.petvaccine.model.Privilege;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrivilegeRepository extends MongoRepository<Privilege, String> {
    Optional<Privilege> findByName(String name);
}
