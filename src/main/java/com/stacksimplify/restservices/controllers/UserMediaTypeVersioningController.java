package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.Services.UserService;
import com.stacksimplify.restservices.dtos.UserDtoV1;
import com.stacksimplify.restservices.dtos.UserDtoV2;
import com.stacksimplify.restservices.exceptions.UserNotFound;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.util.Optional;

@RestController
@RequestMapping(value = "/versioning/media/users")
public class UserMediaTypeVersioningController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping(value = "{id}", produces = "application/vnd.stacksimplify.app-v1+json")
    public UserDtoV1 getUserById(@PathVariable("id") @Min(value = 1) Long id) throws UserNotFound {

        Optional<User> OpUser = userService.getUserById(id);
        if(!OpUser.isPresent()){
            throw new UserNotFound("User not found");
        }
        User user = OpUser.get();

        UserDtoV1 userDtoV1 = modelMapper.map(user,UserDtoV1.class);
        return userDtoV1;
    }

    @GetMapping(value = "{id}", produces = "application/vnd.stacksimplify.app-v2+json")
    public UserDtoV2 getUserById2(@PathVariable("id") @Min(value = 1) Long id) throws UserNotFound {

        Optional<User> OpUser = userService.getUserById(id);
        if(!OpUser.isPresent()){
            throw new UserNotFound("User not found");
        }
        User user = OpUser.get();

        UserDtoV2 userDtoV2 = modelMapper.map(user,UserDtoV2.class);
        return userDtoV2;
    }
}
