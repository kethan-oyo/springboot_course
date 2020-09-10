package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.Services.UserService;
import com.stacksimplify.restservices.exceptions.UserExistsException;
import com.stacksimplify.restservices.exceptions.UserNameNotFoundException;
import com.stacksimplify.restservices.exceptions.UserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users")
    public List<User> getAllUsers(){

        return userService.getAllUsers();
    }

    @PostMapping("/users")
    public ResponseEntity<Void> createUser(@Valid @RequestBody User user, UriComponentsBuilder builder){
        try{
            userService.createUser(user);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(builder.path("/users/{id}").buildAndExpand(user.getId()).toUri());
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } catch(UserExistsException ee){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ee.getMessage());
        }

    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable("id") @Min(value = 1) Long id){
        try {
            return userService.getUserById(id);
        } catch (UserNotFound ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }

    @PutMapping("/users/{id}")
    public User updateUserById(@RequestBody User user, @PathVariable("id") Long id){
        try{
            return userService.updateUserById(user, id);
        }catch (UserNotFound ex){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ex.getMessage());
        }

    }

    @DeleteMapping("/users/{id}")
    public void deleteUserById(@PathVariable("id") Long id){
        userService.deleteUserById(id);
    }

    @GetMapping("/users/byusername/{username}")
    public User getUserByUsername(@PathVariable("username") String username) throws UserNameNotFoundException {

        User user = userService.getUserByUername(username);
        if(user == null){
            throw new UserNameNotFoundException("Username: " + username + " not found in user repository");
        }
        return userService.getUserByUername(username);
    }

}
