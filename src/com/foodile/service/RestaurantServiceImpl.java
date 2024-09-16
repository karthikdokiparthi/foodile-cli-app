package com.foodile.service;

import com.foodile.exceptions.DishNotFoundException;
import com.foodile.exceptions.RestaurantNotFoundException;
import com.foodile.exceptions.RestaurantExistsException;
import com.foodile.model.Dish;
import com.foodile.model.Restaurants;
import com.foodile.repository.RestaurantRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public List<Restaurants> getRestaurantList() {
        return this.restaurantRepository.getRestaurantList();
    }

    @Override
    public Restaurants save(Restaurants restaurant) throws RestaurantExistsException {
        Optional<Restaurants> restaurantById = this.restaurantRepository.findRestaurantById(restaurant.getId());
        if (restaurantById.isPresent()) {
            throw new RestaurantExistsException("Restaurant Already Exists with this Id  :" + restaurant.getId());
        }
        return this.restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurants getRestaurantById(String id) throws RestaurantNotFoundException {
        Optional<Restaurants> restaurantById = this.restaurantRepository.findRestaurantById(id);
        if (restaurantById.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant Not Found with this Id  :" + id);
        }
        return restaurantById.get();
    }

    @Override
    public Restaurants updateRestaurant(Restaurants restaurant) throws RestaurantNotFoundException {
        Optional<Restaurants> restaurantById = this.restaurantRepository.findRestaurantById(restaurant.getId());
        if (restaurantById.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant Not Found with this Id  :" + restaurant.getId());
        }
        return this.restaurantRepository.updateRestaurant(restaurant);
    }

    @Override
    public void deleteRestaurant(String id) throws RestaurantNotFoundException {
        Optional<Restaurants> restaurantById = this.restaurantRepository.findRestaurantById(id);
        if (restaurantById.isEmpty()) {
            throw new RestaurantNotFoundException("Restaurant Not Found with this Id  :" + id);
        }
        this.restaurantRepository.deleteRestaurant(restaurantById.get());
    }

    @Override
    public List<Dish> getDishItems(String id) throws RestaurantNotFoundException, DishNotFoundException {
        // Similar implementation for dishes, using a DishService that interacts with a PostgreSQL DB.
        return new ArrayList<>(); // Placeholder for dish retrieval logic
    }
}
