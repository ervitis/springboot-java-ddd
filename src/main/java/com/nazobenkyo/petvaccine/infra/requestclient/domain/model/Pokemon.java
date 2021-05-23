package com.nazobenkyo.petvaccine.infra.requestclient.domain.model;

import lombok.Data;

@Data
public class Pokemon {
    private String id;
    private String name;
    private double height;
    private double weight;
}
