package com.foodile.exceptions;

public class OrderExistsException extends Exception{
    public OrderExistsException(String message) {
        super(message);
    }
}
