package com.nazobenkyo.petvaccine.domain.repository.extended.impl;

import com.nazobenkyo.petvaccine.application.api.exception.model.UserException;
import com.nazobenkyo.petvaccine.domain.repository.extended.IUserRepositoryExtended;
import com.nazobenkyo.petvaccine.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryExtended implements IUserRepositoryExtended {
    private final MongoTemplate mongoTemplate;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRepositoryExtended(MongoTemplate mongoTemplate, PasswordEncoder passwordEncoder) {
        this.mongoTemplate = mongoTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User update(User user) {
        Update update = new Update();
        Query query = new Query(new Criteria().andOperator(Criteria.where("_id").is(user.getId())));

        if (user.getPassword() != null) {
            update.set("password", this.passwordEncoder.encode(user.getPassword()));
        }
        if (user.getEnabled() != null) {
            update.set("enabled", user.getEnabled());
        }
        if (user.getFirstName() != null) {
            update.set("firstName", user.getFirstName());
        }
        if (user.getLastName() != null) {
            update.set("lastName", user.getLastName());
        }
        if (user.getEmail() != null) {
            update.set("email", user.getEmail());
        }
        if (user.getAccountNonExpired() != null) {
            update.set("accountNonExpired", user.getAccountNonExpired());
        }

        if (this.mongoTemplate.updateFirst(query, update, User.class).getModifiedCount() != 1) {
            throw new UserException("error updating data");
        }
        return this.mongoTemplate.findOne(query, User.class);
    }
}
