package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.Entity.Order;
import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.Services.UserService;
import com.stacksimplify.restservices.exceptions.UserNotFound;
import com.stacksimplify.restservices.repositories.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Min;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hateoas/users")
@Validated
public class UserHateoasController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping()
    public CollectionModel<User> getAllUsers() throws UserNotFound{

        List<User> allUsers = userService.getAllUsers();

        for(User user: allUsers){
            Long userId = user.getId();
            Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
            user.add(selfLink);

            //Relationship with getAllOrders
            CollectionModel<Order> orders = WebMvcLinkBuilder.methodOn(OrderHateoasController.class).getAllOrders(userId);
            Link ordersLink = WebMvcLinkBuilder.linkTo(orders).withRel("all-orders");
            user.add(ordersLink);
        }
        //SelfLink
        Link slink = WebMvcLinkBuilder.linkTo(this.getClass()).withSelfRel();
        CollectionModel<User> finalEntity = new CollectionModel<User>(allUsers,slink);
        return finalEntity;
    }

    @GetMapping("/{id}")
    public EntityModel<User> getUserById(@PathVariable("id") @Min(value = 1) Long id){
        try {

           Optional<User> Opuser = userService.getUserById(id);
           User user = Opuser.get();
           Long userId = user.getId();
           Link selfLink = WebMvcLinkBuilder.linkTo(this.getClass()).slash(userId).withSelfRel();
           user.add(selfLink);
           EntityModel<User> finalEntity = new EntityModel<User>(user);
           return finalEntity;
        } catch (UserNotFound ex){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
        }

    }
}
