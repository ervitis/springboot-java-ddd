package com.nazobenkyo.petvaccine.domain.repository.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class Vaccine {
    @Id
    public String id;

    @Indexed
    public String name;

    public short yearToInject;
}
