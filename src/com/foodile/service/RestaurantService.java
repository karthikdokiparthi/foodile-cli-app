package com.foodile.service;

import com.foodile.exceptions.DishNotFoundException;
import com.foodile.exceptions.RestaurantNotFoundException;
import com.foodile.exceptions.RestaurantExistsException;
import com.foodile.model.Dish;
import com.foodile.model.Restaurants;

import java.util.List;

public interface RestaurantService {
    List<Restaurants> getRestaurantList();
    Restaurants save(Restaurants restaurant) throws RestaurantExistsException;
    Restaurants getRestaurantById(String id) throws RestaurantNotFoundException;
    Restaurants updateRestaurant(Restaurants restaurant) throws RestaurantNotFoundException;
    void deleteRestaurant(String id) throws RestaurantNotFoundException;
    List<Dish> getDishItems(String id) throws RestaurantNotFoundException, DishNotFoundException;
}
