package com.stacksimplify.restservices.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.Entity.Views;
import com.stacksimplify.restservices.Services.UserService;
import com.stacksimplify.restservices.exceptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@Validated
@RequestMapping(value = "/jsonview/users")
public class UserJsonViewController {


    // Autowire the UserService
    @Autowired
    private UserService userService;

    // getUserById - External
    @JsonView(Views.External.class)
    @GetMapping("/external/{id}")
    public Optional<User> getUserById(@PathVariable("id") @Min(1) Long id) {

        try {
            return userService.getUserById(id);
        } catch (UserNotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    // getUserById - Internal
    @GetMapping("/internal/{id}")
    @JsonView(Views.Internal.class)
    public Optional<User> getUserById2(@PathVariable("id") @Min(1) Long id) {

        try {
            return userService.getUserById(id);
        } catch (UserNotFound ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }


}

