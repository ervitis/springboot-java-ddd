package com.nazobenkyo.petvaccine.application.api.domain;

import com.nazobenkyo.petvaccine.application.api.exception.model.ErrorResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/default")
public interface IPetApiController<PetCreate> {

    @ApiOperation(value = "Create a pet")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pet created"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Not authenticated", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/pet", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    PetCreate create(@RequestBody @Valid PetCreate request);

    @ApiOperation(value = "Get a pet by owner email")
    @ApiResponses({
            @ApiResponse(code = 201, message = "Pet retrieved"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Not authenticated", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class)
    })
    @GetMapping(value = "/pet/{emailOwner}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, params = "id")
    PetCreate getPet(@PathVariable("emailOwner") String emailOwner);

    @ApiOperation(value = "Update a pet using its ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pet updated"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Not authenticated", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class)
    })
    @PutMapping(value = "/pet/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, params = "id")
    PetCreate update(@RequestBody @Valid PetCreate request, @PathVariable("id") String id);

    @ApiOperation(value = "Delete a pet using its ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Pet deleted"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Not authenticated", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class),
            @ApiResponse(code = 404, message = "Not Found", response = ErrorResponse.class)
    })
    @DeleteMapping(value = "/pet/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE, params = "id")
    void delete(@PathVariable String id);

    @ApiOperation(value = "Get a paginated data of pets from the system")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Pet list"),
            @ApiResponse(code = 400, message = "Bad request", response = ErrorResponse.class),
            @ApiResponse(code = 401, message = "Not authenticated", response = ErrorResponse.class),
            @ApiResponse(code = 403, message = "Forbidden", response = ErrorResponse.class)
    })
    @GetMapping(value = "/pets", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Page<PetCreate> getPets(@RequestParam(value = "page") int page, @RequestParam(value = "size") int size);
}
