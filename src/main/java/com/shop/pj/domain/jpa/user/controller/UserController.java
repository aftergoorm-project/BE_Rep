package com.shop.pj.domain.jpa.user.controller;

import com.shop.pj.domain.jpa.user.service.UserService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
