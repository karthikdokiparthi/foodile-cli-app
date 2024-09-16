package com.foodile.service;

import com.foodile.model.Dish;
import com.foodile.repository.DishRepository;
import com.foodile.exceptions.DishExistsException;
import com.foodile.exceptions.DishNotFoundException;

import java.util.List;

public class DishServiceImpl {
    private final DishRepository dishRepository;

    public DishServiceImpl() {
        this.dishRepository = new DishRepository(); // Initialize with default repository
    }

    public List<Dish> getDishesList() {
        return dishRepository.getDishList();
    }

    public Dish save(Dish dish) throws DishExistsException {
        if (dishRepository.findDishById(dish.getId()).isPresent()) {
            throw new DishExistsException("Dish already exists with id: " + dish.getId());
        }
        return dishRepository.saveDish(dish);
    }

    public Dish getDishById(String id) throws DishNotFoundException {
        return dishRepository.findDishById(id).orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + id));
    }

    public Dish update(Dish dish) throws DishNotFoundException {
        if (dishRepository.findDishById(dish.getId()).isEmpty()) {
            throw new DishNotFoundException("Dish not found with id: " + dish.getId());
        }
        return dishRepository.updateDish(dish);
    }

    public void delete(String id) throws DishNotFoundException {
        Dish dish = dishRepository.findDishById(id).orElseThrow(() -> new DishNotFoundException("Dish not found with id: " + id));
        dishRepository.deleteDish(dish);
    }
}
