package com.nazobenkyo.petvaccine.application.api.domain.v1;

import com.nazobenkyo.petvaccine.application.api.domain.v1.model.PetCreate;
import com.nazobenkyo.petvaccine.application.api.security.domain.privileges.PrivilegeConstants;
import com.nazobenkyo.petvaccine.application.api.security.domain.roles.RoleConstants;
import com.nazobenkyo.petvaccine.domain.mapper.ToByteArray;
import com.nazobenkyo.petvaccine.security.generator.JWTAuth0Token;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
@WebAppConfiguration
@SpringBootTest
public class PetApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final JWTAuth0Token jwtAuth0Token = new JWTAuth0Token();

    @Test
    @WithUserDetails("doctor@petvaccine.com")
    @WithMockUser(username = "doctor@petvaccine.com", roles = {"ROLE_DOCTOR"}, password = "123456",
            authorities = {"CREATE_PETS_PRIVILEGE", "UPDATE_PETS_PRIVILEGE", "DELETE_PETS_PRIVILEGE", "GET_PETS_PRIVILEGE"})
    public void whenCreatePet_thenReturnsPetData() throws Exception {
        PetCreate body = PetCreate.builder().name("doggy").ownerEmail("owner@test.com").type("Golden").build();
        MvcResult result = mockMvc.perform(
                post("/v1/pet")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + jwtAuth0Token.createToken("doctor@petvaccine.com",  PrivilegeConstants.DOCTOR_PRIVILEGES, Collections.singletonList(RoleConstants.ROLE_DOCTOR)).getJwtValue())
                    .content(ToByteArray.fromObject(body))
        )
                .andExpect(status().isCreated())
                .andReturn();
        System.out.println(result.getResponse().getContentAsString());
    }
}
