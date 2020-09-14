package com.stacksimplify.restservices.dtos;

import com.stacksimplify.restservices.Entity.Order;

import java.util.List;

public class UserMmDto {

    private Long id;
    private String usernme;
    private String firstname;
    private List<Order> orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsernme() {
        return usernme;
    }

    public void setUsernme(String usernme) {
        this.usernme = usernme;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
}
