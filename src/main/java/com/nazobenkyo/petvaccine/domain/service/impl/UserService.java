package com.nazobenkyo.petvaccine.domain.service.impl;

import com.nazobenkyo.petvaccine.application.api.exception.model.NotFoundException;
import com.nazobenkyo.petvaccine.application.api.exception.model.UserAlreadyExists;
import com.nazobenkyo.petvaccine.application.api.exception.model.UserException;
import com.nazobenkyo.petvaccine.application.api.security.domain.roles.RoleConstants;
import com.nazobenkyo.petvaccine.domain.repository.RoleRepository;
import com.nazobenkyo.petvaccine.domain.repository.UserRepository;
import com.nazobenkyo.petvaccine.domain.repository.extended.impl.UserRepositoryExtended;
import com.nazobenkyo.petvaccine.domain.service.IUserService;
import com.nazobenkyo.petvaccine.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserRepositoryExtended userRepositoryExtended;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepositoryExtended userRepositoryExtended) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryExtended = userRepositoryExtended;
    }

    @Override
    public User create(User user) throws UserAlreadyExists {
        if (this.emailExists(user.getEmail())) {
            throw new UserAlreadyExists("user is already registered in the system");
        }

        user.setRoles(Collections.singletonList(this.roleRepository.findByName(RoleConstants.ROLE_USER).orElseThrow()));
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }

    @Override
    public User get(User user) throws NotFoundException {
        return this.userRepository.findById(user.getId()).orElseThrow(NotFoundException::new);
    }

    @Override
    public User update(User user) throws UserException {
        return this.userRepositoryExtended.update(user);
    }

    @Override
    public Page<User> getAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }

    @Override
    public void delete(String id) throws NotFoundException {
        User entity = this.userRepository.findById(id).orElseThrow(NotFoundException::new);
        this.userRepository.delete(entity);
    }

    boolean emailExists(String email) {
        return this.userRepository.findByEmail(email).isPresent();
    }
}
