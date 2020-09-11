package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.Entity.Order;
import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.exceptions.UserNotFound;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hateoas/users")
public class OrderHateoasController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userId}/orders")
    public CollectionModel<Order> getAllOrders(@PathVariable Long userId) throws UserNotFound {

        Optional<User> opUser = userRepository.findById(userId);

        if(!opUser.isPresent()){
            throw new UserNotFound("User not found");
        }

        List<Order> allOrders = opUser.get().getOrders();
        CollectionModel<Order> finalOrder = new CollectionModel<Order>(allOrders);
        return finalOrder;
    }

}
