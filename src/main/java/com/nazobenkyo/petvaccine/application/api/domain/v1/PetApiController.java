package com.nazobenkyo.petvaccine.application.api.domain.v1;

import com.nazobenkyo.petvaccine.application.api.domain.IPetApiController;
import com.nazobenkyo.petvaccine.application.api.domain.v1.model.PetCreate;
import com.nazobenkyo.petvaccine.domain.service.IPetService;
import com.nazobenkyo.petvaccine.domain.mapper.PetMapper;
import com.nazobenkyo.petvaccine.model.Pet;
import io.swagger.annotations.Api;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1")
@RestController
@Api(value = "Pet Controller", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, hidden = true)
public class PetApiController implements IPetApiController<PetCreate> {

    private final PetMapper petMapper;
    private final IPetService petService;

    public PetApiController(IPetService petService) {
        this.petMapper = Mappers.getMapper(PetMapper.class);
        this.petService = petService;
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_DOCTOR')")
    public PetCreate create(PetCreate request) {
        Pet petDto = this.petMapper.petRequestToPetDto(request);
        return this.petMapper.petDtoToPetCreate(this.petService.create(petDto));
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_DOCTOR')")
    public PetCreate getPet(String email) {
        return this.petMapper.petDtoToPetCreate(this.petService.get(email));
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_DOCTOR')")
    public PetCreate update(PetCreate request, String id) {
        Pet pet = this.petMapper.petRequestToPetDto(request);
        pet.setId(id);
        return this.petMapper.petDtoToPetCreate(this.petService.update(pet));
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_DOCTOR')")
    public void delete(String id) {
        this.petService.delete(id);
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_DOCTOR')")
    public Page<PetCreate> getPets(int page, int size) {
        return this.petService.getAll(PageRequest.of(page, size)).map(this.petMapper::petDtoToPetCreate);
    }
}
