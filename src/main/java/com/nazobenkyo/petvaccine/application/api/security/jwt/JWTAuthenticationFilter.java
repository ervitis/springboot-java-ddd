package com.nazobenkyo.petvaccine.application.api.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nazobenkyo.petvaccine.application.api.exception.model.NotFoundException;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.JWTModel;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.JWTProvider;
import com.nazobenkyo.petvaccine.domain.repository.UserRepository;
import com.nazobenkyo.petvaccine.domain.service.userdetail.ClinicUserDetailsService;
import com.nazobenkyo.petvaccine.model.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ClinicUserDetailsService clinicUserDetailsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public JWTAuthenticationFilter(ClinicUserDetailsService clinicUserDetailsService, AuthenticationManager authenticationManager, UserRepository userRepository) {
        this.clinicUserDetailsService = clinicUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User authUser;
        try {
            authUser = new ObjectMapper().readValue(request.getInputStream(), User.class);
        } catch (IOException exception) {
            throw new AuthenticationServiceException("wrong data");
        }
        UserDetails userDetails = this.clinicUserDetailsService.loadUserByUsername(authUser.getEmail());
        return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDetails.getUsername(), authUser.getPassword(), userDetails.getAuthorities()));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) {
        JWTProvider provider = new JWTProvider();
        User user = this.userRepository.findByEmail(((org.springframework.security.core.userdetails.User) authResult.getPrincipal()).getUsername()).orElseThrow(NotFoundException::new);
        try {
            JWTModel jwtModel = provider.create(authResult, user);
            response.addHeader(JWTConstants.HEADER_AUTHENTICATION_KEY, String.format("%s %s", JWTConstants.HEADER_AUTHENTICATION_BEARER, jwtModel.getJwtValue()));
        } catch (Exception exception) {
            throw new RuntimeException(exception);
        }
    }
}
