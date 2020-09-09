package com.stacksimplify.restservices.exceptions;

public class UserNotFound extends Exception{
    private static final long serialVersionUID = -2765432893920453225L;

    public UserNotFound(String message) {
        super(message);
    }
}
