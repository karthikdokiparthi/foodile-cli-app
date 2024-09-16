package com.foodile.service;

import com.foodile.exceptions.DishExistsException;
import com.foodile.exceptions.DishNotFoundException;
import com.foodile.model.Dish;

import java.util.List;

public interface DishService {
    List<Dish> getDishesList();
    Dish save(Dish dish) throws DishExistsException;
    Dish getDishById(String id) throws DishNotFoundException;
    Dish update(Dish dish) throws DishNotFoundException;
    void delete(String id) throws DishNotFoundException;
}
