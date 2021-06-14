package com.nazobenkyo.petvaccine.application.api.domain.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nazobenkyo.petvaccine.application.api.domain.v1.model.PetCreate;
import com.nazobenkyo.petvaccine.application.api.security.domain.privileges.PrivilegeConstants;
import com.nazobenkyo.petvaccine.application.api.security.domain.roles.RoleConstants;
import com.nazobenkyo.petvaccine.domain.mapper.ToByteArray;
import com.nazobenkyo.petvaccine.domain.repository.IPetRepository;
import com.nazobenkyo.petvaccine.model.Pet;
import com.nazobenkyo.petvaccine.security.generator.JWTAuth0Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
@WebAppConfiguration
@SpringBootTest
@WithUserDetails("doctor@petvaccine.com")
@WithMockUser(username = "doctor@petvaccine.com", roles = {"ROLE_DOCTOR"}, password = "123456",
        authorities = {"CREATE_PETS_PRIVILEGE", "UPDATE_PETS_PRIVILEGE", "DELETE_PETS_PRIVILEGE", "GET_PETS_PRIVILEGE"})
public class PetApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final JWTAuth0Token jwtAuth0Token = new JWTAuth0Token();

    private final ObjectMapper mapper = new ObjectMapper();

    @MockBean
    private IPetRepository repository;

    @Test
    public void whenCreatePet_thenReturnsPetData() throws Exception {
        PetCreate body = PetCreate.builder().name("doggy").ownerEmail("owner@test.com").type("Golden").build();
        Pet pet = Pet.builder().name(body.getName()).ownerEmail(body.getOwnerEmail()).type(body.getType()).id("123456").build();

        when(this.repository.findByOwnerEmail(any())).thenReturn(Optional.empty());
        when(this.repository.save(any())).thenReturn(pet);

        MvcResult result = mockMvc.perform(
                post("/v1/pet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAuth0Token.createToken("doctor@petvaccine.com",  PrivilegeConstants.DOCTOR_PRIVILEGES, Collections.singletonList(RoleConstants.ROLE_DOCTOR)).getJwtValue())
                    .content(ToByteArray.fromObject(body))
        )
                .andExpect(status().isCreated())
                .andReturn();
        body = mapper.readValue(result.getResponse().getContentAsString(), PetCreate.class);
        Assertions.assertNotNull(body);
        Assertions.assertEquals("123456", body.getId());
    }

    @Test
    public void whenCreatePetAlreadyExists_thenReturnsPetData() throws Exception {
        PetCreate body = PetCreate.builder().name("doggy").ownerEmail("owner@test.com").type("Golden").build();
        Pet pet = Pet.builder().name(body.getName()).ownerEmail(body.getOwnerEmail()).type(body.getType()).id("123456").build();

        when(this.repository.findByOwnerEmail(any())).thenReturn(Optional.of(pet));

        MvcResult result = mockMvc.perform(
                post("/v1/pet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAuth0Token.createToken("doctor@petvaccine.com",  PrivilegeConstants.DOCTOR_PRIVILEGES, Collections.singletonList(RoleConstants.ROLE_DOCTOR)).getJwtValue())
                        .content(ToByteArray.fromObject(body))
        )
                .andExpect(status().isCreated())
                .andReturn();
        body = mapper.readValue(result.getResponse().getContentAsString(), PetCreate.class);
        Assertions.assertNotNull(body);
        Assertions.assertEquals("123456", body.getId());
    }

    @Test
    public void whenCreatePetWithWrongToken_thenReturnsForbidden() throws Exception {
        PetCreate body = PetCreate.builder().name("doggy").ownerEmail("owner@test.com").type("Golden").build();

        mockMvc.perform(
                post("/v1/pet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAuth0Token.createToken("doctor@petvaccine.com",  PrivilegeConstants.DOCTOR_PRIVILEGES, Collections.singletonList(RoleConstants.ROLE_USER)).getJwtValue())
                        .content(ToByteArray.fromObject(body))
        )
                .andExpect(status().isForbidden());
    }

    @Test
    public void whenAGetPet_thenReturnsPetData() throws Exception {
        PetCreate body = PetCreate.builder().name("doggy").ownerEmail("owner@test.com").type("Golden").build();
        Pet pet = Pet.builder().name(body.getName()).ownerEmail(body.getOwnerEmail()).type(body.getType()).id("123456").build();

        when(this.repository.findByOwnerEmail(any())).thenReturn(Optional.of(pet));

        MvcResult result = mockMvc.perform(
                get("/v1/pet")
                        .param("email", "owner@test.com")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Allow", "GET")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAuth0Token.createToken("doctor@petvaccine.com",  PrivilegeConstants.DOCTOR_PRIVILEGES, Collections.singletonList(RoleConstants.ROLE_DOCTOR)).getJwtValue())
        )
                .andExpect(status().isOk())
                .andReturn();
        body = mapper.readValue(result.getResponse().getContentAsString(), PetCreate.class);
        Assertions.assertNotNull(body);
        Assertions.assertEquals("123456", body.getId());
    }

    @Test
    public void whenAGetPetThatDoesnotExists_thenReturnNotFound() throws Exception {
        when(this.repository.findByOwnerEmail(any())).thenReturn(Optional.empty());

        mockMvc.perform(
                get("/v1/pet")
                        .param("email", "owner@test.com")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAuth0Token.createToken("doctor@petvaccine.com",  PrivilegeConstants.DOCTOR_PRIVILEGES, Collections.singletonList(RoleConstants.ROLE_DOCTOR)).getJwtValue())
        )
                .andExpect(status().isNotFound());
    }

}
