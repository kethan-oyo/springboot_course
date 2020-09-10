package com.stacksimplify.restservices.controllers;

import com.stacksimplify.restservices.Entity.Order;
import com.stacksimplify.restservices.Entity.User;
import com.stacksimplify.restservices.exceptions.UserNotFound;
import com.stacksimplify.restservices.repositories.OrderRepository;
import com.stacksimplify.restservices.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users")
public class OrderController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @GetMapping("/{userId}/orders")
    public List<Order> getAllOrders(@PathVariable Long userId) throws UserNotFound{

        Optional<User> opUser = userRepository.findById(userId);

        if(!opUser.isPresent()){
            throw new UserNotFound("User not found");
        }

        return opUser.get().getOrders();
    }

    //Create Order Method

    @PostMapping("/{userId}/order")
    public Order createOrder(@PathVariable Long userId, @RequestBody Order order) throws UserNotFound{

        Optional<User> opUser = userRepository.findById(userId);

        if(!opUser.isPresent()){
            throw new UserNotFound("User not found");
        }

        User user = opUser.get();
        order.setUser(user);

        return orderRepository.save(order);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public Optional<Order> getOrderByOrderId(@PathVariable Long userId, @PathVariable Long orderId) throws UserNotFound{

        Optional<User> opUser = userRepository.findById(userId);

        if(!opUser.isPresent()){
            throw new UserNotFound("User not found");
        }

        Optional<Order> opOrder = orderRepository.findById(orderId);
        return opOrder;
    }
}
