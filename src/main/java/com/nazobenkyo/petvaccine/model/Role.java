package com.nazobenkyo.petvaccine.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Collection;

@Data
public class Role {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiModelProperty(hidden = true)
    private String name;

    public Role(String name) {
        this.name = name;
    }

    @DBRef
    private Collection<Privilege> privileges;
}
