package com.nazobenkyo.petvaccine.domain.service;

import com.nazobenkyo.petvaccine.application.api.exception.model.NotFoundException;
import com.nazobenkyo.petvaccine.application.api.exception.model.UserAlreadyExists;
import com.nazobenkyo.petvaccine.application.api.exception.model.UserException;
import com.nazobenkyo.petvaccine.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface IUserService {
    User create(User user) throws UserAlreadyExists;

    User get(User user) throws NotFoundException;

    User update(User user) throws UserException;

    Page<User> getAll(Pageable pageable);

    void delete(String id) throws NotFoundException;
}
