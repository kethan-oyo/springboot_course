package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.dtos.UserMsDto;
import com.stacksimplify.restservices.mappers.UserMapper;
import com.stacksimplify.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/mapstruct/users")
public class UserMapStructController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @GetMapping
    public List<UserMsDto> getAllUserDto(){
        return userMapper.usersToUserMsDto(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public UserMsDto getUserById(@PathVariable Long id){
        Optional<User> OpUser = userRepository.findById(id);
        User user = OpUser.get();
        return userMapper.userToUserMsDto(user);
    }
}
