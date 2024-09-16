package com.foodile.controller;

import com.foodile.exceptions.RestaurantExistsException;
import com.foodile.exceptions.RestaurantNotFoundException;
import com.foodile.model.Restaurants;
import com.foodile.repository.RestaurantRepository;
import com.foodile.service.RestaurantServiceImpl;

import java.util.List;

public class RestaurantController {
    private final RestaurantServiceImpl restaurantService;

    public RestaurantController(RestaurantServiceImpl restaurantService) {
        this.restaurantService = restaurantService;
    }

    public List<Restaurants> getRestaurantList(){
        return this.restaurantService.getRestaurantList();
    }

    public Restaurants saveRestaurant(Restaurants restaurant) throws RestaurantExistsException {
        return this.restaurantService.save(restaurant);
    }

    public Restaurants getRestaurantById(String id) throws RestaurantNotFoundException {
        return this.restaurantService.getRestaurantById(id);
    }

    public Restaurants updateRestaurant(Restaurants restaurant) throws RestaurantNotFoundException{
        return this.restaurantService.updateRestaurant(restaurant);
    }

    public void deleteRestaurant(String id) throws RestaurantNotFoundException {
        this.restaurantService.deleteRestaurant(id);
    }
}
