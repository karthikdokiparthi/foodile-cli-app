package com.foodile.controller;

import com.foodile.exceptions.DishExistsException;
import com.foodile.exceptions.DishNotFoundException;
import com.foodile.model.Dish;
import com.foodile.service.DishServiceImpl;

import java.util.List;

public class DishController {
    private final DishServiceImpl dishService;

    public DishController(DishServiceImpl dishService) {
        this.dishService = dishService;
    }

    public List<Dish> getDisesList() {
        return this.dishService.getDishesList();
    }

    public Dish save(Dish dish) throws DishExistsException {
        return this.dishService.save(dish);
    }

    public Dish getDishById(String id) throws DishNotFoundException {
        return this.dishService.getDishById(id);
    }

    public Dish update(Dish dish) throws DishNotFoundException {
        return this.dishService.update(dish);
    }

    public void deleteDish(String id) throws DishNotFoundException {
        this.dishService.delete(id);
    }
}
