package com.nazobenkyo.petvaccine.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Privilege {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(hidden = true)
    private String name;

    public Privilege(String name) {
        this.name = name;
    }
}
