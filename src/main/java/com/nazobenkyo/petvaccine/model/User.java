package com.nazobenkyo.petvaccine.model;

import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.Collection;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @ApiModelProperty(hidden = true)
    private String id;

    @ApiParam
    private String firstName;

    @ApiParam
    private String lastName;

    @Indexed
    @ApiParam
    private String email;

    @ApiModelProperty(hidden = true)
    private String password;

    @ApiParam
    private Boolean enabled;

    @ApiModelProperty(hidden = true)
    private boolean tokenExpired;

    @ApiModelProperty(hidden = true)
    private Boolean accountNonExpired;

    @ApiModelProperty(hidden = true)
    private boolean credentialsNonExpired = true;

    @ApiModelProperty(hidden = true)
    private boolean accountNonLocked = true;

    @DBRef
    @ApiModelProperty(hidden = true)
    private Collection<Role> roles;
}
