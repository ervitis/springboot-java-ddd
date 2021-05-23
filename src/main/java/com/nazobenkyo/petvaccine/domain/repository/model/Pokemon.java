package com.nazobenkyo.petvaccine.domain.repository.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

@Data
public class Pokemon {
    @Id
    private String id;
    @Indexed
    private String name;
    private double height = 0.0;
    private double weight = 0.0;
}
