package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.Services.UserService;
import com.stacksimplify.restservices.dtos.UserMmDto;
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
@RequestMapping(value = "/modelmapper/users")
public class UserModelMapperController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/{id}")
    public UserMmDto getUserById(@PathVariable("id") @Min(value = 1) Long id) throws UserNotFound {

        Optional<User> OpUser = userService.getUserById(id);
        if(!OpUser.isPresent()){
            throw new UserNotFound("User not found");
        }
        User user = OpUser.get();

        UserMmDto userMmDto = modelMapper.map(user,UserMmDto.class);
        return userMmDto;
    }
}
