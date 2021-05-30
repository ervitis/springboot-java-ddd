package com.nazobenkyo.petvaccine.application.api.configuration;

import com.nazobenkyo.petvaccine.application.api.exception.handler.CustomAccessDeniedHandler;
import com.nazobenkyo.petvaccine.application.api.exception.handler.CustomAuthenticationEntrypoint;
import com.nazobenkyo.petvaccine.application.api.security.jwt.JWTAuthenticationFilter;
import com.nazobenkyo.petvaccine.application.api.security.jwt.JWTAuthorizationFilter;
import com.nazobenkyo.petvaccine.domain.repository.UserRepository;
import com.nazobenkyo.petvaccine.domain.service.userdetail.ClinicUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
public class Security extends WebSecurityConfigurerAdapter {

    private final ClinicUserDetailsService clinicUserDetailsService;
    private final UserRepository userRepository;

    @Autowired
    public Security(ClinicUserDetailsService clinicUserDetailsService, UserRepository userRepository) {
        this.clinicUserDetailsService = clinicUserDetailsService;
        this.userRepository = userRepository;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler()).authenticationEntryPoint(new CustomAuthenticationEntrypoint())
                .and().authorizeRequests()
                    .antMatchers("/actuator/**").permitAll()
                    .antMatchers("/doc/**", "/v2/api-docs/**").permitAll()
                    .antMatchers(HttpMethod.POST, "/login").permitAll()
                    .anyRequest().authenticated()
                .and()
                    .addFilter(new JWTAuthenticationFilter(this.clinicUserDetailsService, authenticationManager(), this.userRepository))
                    .addFilter(new JWTAuthorizationFilter(this.clinicUserDetailsService, authenticationManager()))
                .cors()
                .and().csrf().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) {
        authenticationManagerBuilder.authenticationProvider(authProvider());
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(this.clinicUserDetailsService);
        authProvider.setPasswordEncoder(encoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }
}
