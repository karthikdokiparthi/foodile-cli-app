package com.foodile.exceptions;

public class DishExistsException extends Exception{
    public DishExistsException(String message) {
        super(message);
    }
}
