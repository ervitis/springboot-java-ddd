package com.nazobenkyo.petvaccine.application.listeners;

import com.nazobenkyo.petvaccine.application.api.security.privileges.PrivilegeConstants;
import com.nazobenkyo.petvaccine.application.api.security.roles.RoleConstants;
import com.nazobenkyo.petvaccine.domain.repository.PrivilegeRepository;
import com.nazobenkyo.petvaccine.domain.repository.RoleRepository;
import com.nazobenkyo.petvaccine.domain.repository.UserRepository;
import com.nazobenkyo.petvaccine.model.Privilege;
import com.nazobenkyo.petvaccine.model.Role;
import com.nazobenkyo.petvaccine.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;

    final PrivilegeRepository privilegeRepository;
    final UserRepository userRepository;
    final RoleRepository roleRepository;
    final PasswordEncoder passwordEncoder;

    @Autowired
    public SetupDataLoader(UserRepository userRepository, RoleRepository roleRepository, PrivilegeRepository privilegeRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.privilegeRepository = privilegeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (alreadySetup) return;

        if (this.userRepository.findByEmail("admin@petvaccine.com").isPresent()) {
            return;
        }

        createPrivileges();

        Role adminRole = createRoleIfNotFound(RoleConstants.ROLE_ADMIN, adminPrivileges());
        Role doctorRole = createRoleIfNotFound(RoleConstants.ROLE_DOCTOR, doctorPrivileges());

        this.userRepository.save(createAdminUser(Collections.singletonList(adminRole)));
        this.userRepository.save(createDoctorUser(Collections.singletonList(doctorRole)));

        this.alreadySetup = true;
    }

    private List<Privilege> adminPrivileges() {
        Predicate<Privilege> isAdmin = pr -> PrivilegeConstants.ADMIN_PRIVILEGES.contains(pr.getName());
        return this.getFilteredPrivileges(isAdmin);
    }

    private List<Privilege> doctorPrivileges() {
        Predicate<Privilege> isDoctor = pr -> PrivilegeConstants.DOCTOR_PRIVILEGES.contains(pr.getName());
        return this.getFilteredPrivileges(isDoctor);
    }

    private List<Privilege> getFilteredPrivileges(Predicate<Privilege> predicate) {
        return this.privilegeRepository.findAll().stream().filter(predicate).collect(Collectors.toList());
    }

    private void createPrivileges() {
        PrivilegeConstants.USERS_ALL.forEach(this::createPrivilegeIfNotFound);
        PrivilegeConstants.PETS_ALL.forEach(this::createPrivilegeIfNotFound);
    }

    private User createAdminUser(Collection<Role> roles) {
        return User.builder()
                .firstName("admin")
                .lastName("admin")
                .password(this.passwordEncoder.encode("admin1234"))
                .email("admin@petvaccine.com")
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(roles)
                .build();
    }

    private User createDoctorUser(Collection<Role> roles) {
        return User.builder()
                .firstName("doctor")
                .lastName("doctor")
                .password(this.passwordEncoder.encode("doctor1234"))
                .email("doctor@petvaccine.com")
                .accountNonLocked(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .roles(roles)
                .build();
    }

    @Transactional
    Privilege createPrivilegeIfNotFound(String name) {
        Privilege privilege = this.privilegeRepository.findByName(name).orElse(null);
        if (privilege == null) {
            privilege = new Privilege(name);
            this.privilegeRepository.save(privilege);
        }
        return privilege;
    }

    @Transactional
    Role createRoleIfNotFound(String name, Collection<Privilege> privileges) {
        Role role = this.roleRepository.findByName(name).orElse(null);
        if (role == null) {
            role = new Role(name);
            role.setPrivileges(privileges);
            return this.roleRepository.save(role);
        }
        return role;
    }
}
