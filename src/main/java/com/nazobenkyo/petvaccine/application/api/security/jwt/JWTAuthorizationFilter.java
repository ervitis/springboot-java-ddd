package com.nazobenkyo.petvaccine.application.api.security.jwt;

import com.nazobenkyo.petvaccine.application.api.security.jwt.exception.JWTDecodingException;
import com.nazobenkyo.petvaccine.application.api.security.jwt.exception.JWTSubjectException;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.JWTModel;
import com.nazobenkyo.petvaccine.application.api.security.jwt.jwtprovider.JWTProvider;
import com.nazobenkyo.petvaccine.domain.service.userdetail.ClinicUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final ClinicUserDetailsService clinicUserDetailsService;

    public JWTAuthorizationFilter(ClinicUserDetailsService clinicUserDetailsService, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.clinicUserDetailsService = clinicUserDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(JWTConstants.HEADER_AUTHENTICATION_KEY);
        if (header == null || !header.startsWith(JWTConstants.HEADER_AUTHENTICATION_BEARER)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authenticationToken = getAuthentication(request, response);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, HttpServletResponse response) throws JWTSubjectException, JWTDecodingException {
        String token = request.getHeader(JWTConstants.HEADER_AUTHENTICATION_KEY);
        if (token == null) {
            return null;
        }

        JWTProvider provider = new JWTProvider();
        JWTModel jwtModel = provider.getSubject(token);
        if (jwtModel.getSubject().isEmpty()) {
            return null;
        }
        UserDetails userDetails = this.clinicUserDetailsService.loadUserByUsername(jwtModel.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
    }
}
