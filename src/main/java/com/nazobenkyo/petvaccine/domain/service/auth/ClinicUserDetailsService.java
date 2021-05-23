package com.nazobenkyo.petvaccine.domain.service.auth;

import com.nazobenkyo.petvaccine.application.api.exception.model.NotFoundException;
import com.nazobenkyo.petvaccine.domain.repository.UserRepository;
import com.nazobenkyo.petvaccine.model.Privilege;
import com.nazobenkyo.petvaccine.model.Role;
import com.nazobenkyo.petvaccine.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ClinicUserDetailsService implements UserDetailsService {

    final UserRepository userRepository;

    @Autowired
    public ClinicUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in system");
        }
        boolean isEnabled = user.getEnabled() != null ? user.getEnabled() : true;
        boolean isAccountNonExpired = user.getAccountNonExpired() != null ? user.getAccountNonExpired() : false;
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), isEnabled, isAccountNonExpired, user.isCredentialsNonExpired(), user.isAccountNonLocked(), getAuthorities(user.getRoles())
        );
    }

    public Collection<Role> getRoles(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(NotFoundException::new).getRoles();
    }

    public Collection<Privilege> getPrivileges(String email) {
        return this.userRepository.findByEmail(email).orElseThrow(NotFoundException::new).getRoles().stream().map(Role::getPrivileges).collect(Collectors.toList()).get(0);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        return getGrantedAuthorities(getPrivileges(roles));
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> rolesCollection = new ArrayList<>();
        for (Role role : roles) {
            rolesCollection.addAll(role.getPrivileges());
        }
        for (Privilege item : rolesCollection) {
            privileges.add(item.getName());
        }
        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            authorities.add(new SimpleGrantedAuthority(privilege));
        }
        return authorities;
    }
}
