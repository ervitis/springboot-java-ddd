package com.nazobenkyo.petvaccine.application.api.domain.v1;

import com.nazobenkyo.petvaccine.application.api.domain.IUserApiController;
import com.nazobenkyo.petvaccine.domain.service.IUserService;
import com.nazobenkyo.petvaccine.model.User;
import io.swagger.annotations.Api;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v1/admin")
@RestController
@Api(value = "User Controller", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE, hidden = true)
public class UserApiController implements IUserApiController {
    private final IUserService userService;

    public UserApiController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_ADMIN')")
    public User create(User request) {
        return this.userService.create(request);
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_ADMIN')")
    public User get(User request) {
        return this.userService.get(request);
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_ADMIN')")
    public User update(User request, String id) {
        return this.userService.update(request);
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_ADMIN')")
    public void delete(String id) {
        this.userService.delete(id);
    }

    @Override
    @PreAuthorize("userHasRole('ROLE_ADMIN')")
    public Page<User> getAll(int page, int size) {
        return this.userService.getAll(PageRequest.of(page, size));
    }

}
