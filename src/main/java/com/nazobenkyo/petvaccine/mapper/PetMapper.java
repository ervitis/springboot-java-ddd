package com.nazobenkyo.petvaccine.mapper;

import com.nazobenkyo.petvaccine.application.api.domain.v1.model.PetCreate;
import com.nazobenkyo.petvaccine.model.Pet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PetMapper {
    PetMapper INSTANCE = Mappers.getMapper(PetMapper.class);

    @Mappings(value = {
            @Mapping(target = "updated", ignore = true),
            @Mapping(target = "created", ignore = true)
    })
    Pet petRequestToPetDto(PetCreate petCreate);

    PetCreate petDtoToPetCreate(Pet pet);
}
