package com.nazobenkyo.petvaccine.domain.repository.model;

import com.nazobenkyo.petvaccine.model.Pet;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Date;

@Data
public class VaccinePet {
    @Id
    public String id;

    public Date dateInjected;

    @DBRef
    public Vaccine vaccine;

    @DBRef
    public Pet pet;
}
