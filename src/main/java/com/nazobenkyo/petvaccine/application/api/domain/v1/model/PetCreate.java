package com.nazobenkyo.petvaccine.application.api.domain.v1.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PetCreate {
    @ApiModelProperty(accessMode = ApiModelProperty.AccessMode.READ_WRITE)
    private String id;

    @NotBlank(message = "name is obligatory")
    @Size(min = 2, max = 60, message = "size of name should be between 2 and 60 characters")
    private String name;

    @NotBlank(message = "type is obligatory")
    @Size(min = 3, max = 40, message = "size of type should be between 3 and 40 characters")
    private String type;

    @NotBlank(message = "owner email is obligatory")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = "not a valid email")
    private String ownerEmail;
}
