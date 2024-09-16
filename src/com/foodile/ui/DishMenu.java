package com.foodile.ui;

import com.foodile.controller.DishController;
import com.foodile.exceptions.DishExistsException;
import com.foodile.exceptions.DishNotFoundException;
import com.foodile.model.Dish;

import java.util.List;
import java.util.Scanner;

public class DishMenu extends Menu {
    DishController dishController;

    public DishMenu(DishController dishController) {
        this.dishController = dishController;
    }

    @Override
    public void displayMenu() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                displayMenuHeader("WELCOME TO DISHES SECTION");
                System.out.println();
                System.out.println("Please select the option !");
                System.out.println("--------------------------");
                System.out.println("1. Add New Dish");
                System.out.println("2. View All Dish Items");
                System.out.println("3. Search Dish");
                System.out.println("4. Update Dish ");
                System.out.println("5. Delete Dish");
                System.out.println("6. Exit");

                System.out.println("Please enter your choice (1-6)");

                int input = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                switch (input) {
                    case 1 -> newDishForm();
                    case 2 -> displayDishes();
                    case 3 -> dishSearchForm();
                    case 4 -> dishUpdateForm();
                    case 5 -> dishDeleteForm();
                    case 6 -> {
                        System.out.println("Thank you, See you again!");
                        return;
                    }
                    default -> System.out.println("Invalid Input. Please enter a valid input from (1-6).");
                }
            }
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again!");
            e.printStackTrace();
            displayMenu();
        }
    }

    public void displayDishes() {
        List<Dish> dishList = dishController.getDisesList();
        String dashesLine = new String(new char[150]).replace('\0', '-');
        displayMenuHeader("Dish Items");
        System.out.printf("%-10s %-30s %-80s %-10s\n", "Id", "Name", "Description", "Price");
        System.out.println(dashesLine);
        dishList.forEach(dish -> {
            System.out.printf("%-10s %-30s %-80s %-10s\n", dish.getId(), dish.getName(), dish.getDescription(), String.format("$%.2f", dish.getPrice()));
        });
    }

    public void displayDish(Dish dish) {
        displayMenuHeader("Dish Details");
        System.out.printf("%-10s %-30s %-80s %-10s\n", "Id", "Name", "Description", "Price");
        System.out.println(new String(new char[150]).replace('\0', '-'));
        System.out.printf("%-10s %-30s %-80s %-10s\n", dish.getId(), dish.getName(), dish.getDescription(), String.format("$%.2f", dish.getPrice()));
    }

    public void newDishForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the following details");
            System.out.println("Enter Id");
            String id = scanner.nextLine();
            System.out.println("Enter Name");
            String name = scanner.nextLine();
            System.out.println("Enter Description");
            String description = scanner.nextLine();
            System.out.println("Enter Price");
            double price = scanner.nextDouble();

            Dish dish = new Dish(id, name, description, price);
            dishController.save(dish);
            displayDish(dish);
        } catch (DishExistsException e) {
            System.out.println("Error: Dish already exists with the given id.");
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again!");
            e.printStackTrace();
        }
    }

    public void dishSearchForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Dish Id to search:");
            String id = scanner.nextLine();
            Dish dish = dishController.getDishById(id);
            displayDish(dish);
        } catch (DishNotFoundException e) {
            System.out.println("Error: Dish not found with the given id.");
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again!");
            e.printStackTrace();
        }
    }

    public void dishUpdateForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Dish Id to update:");
            String id = scanner.nextLine();
            Dish dish = dishController.getDishById(id);

            System.out.println("Enter new Name (current: " + dish.getName() + "):");
            String name = scanner.nextLine();
            System.out.println("Enter new Description (current: " + dish.getDescription() + "):");
            String description = scanner.nextLine();
            System.out.println("Enter new Price (current: " + dish.getPrice() + "):");
            double price = scanner.nextDouble();

            dish.setName(name);
            dish.setDescription(description);
            dish.setPrice(price);

            dishController.update(dish);
            displayDish(dish);
        } catch (DishNotFoundException e) {
            System.out.println("Error: Dish not found with the given id.");
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again!");
            e.printStackTrace();
        }
    }

    public void dishDeleteForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter Dish Id to delete:");
            String id = scanner.nextLine();
            dishController.deleteDish(id);
            System.out.println("Dish deleted successfully.");
        } catch (DishNotFoundException e) {
            System.out.println("Error: Dish not found with the given id.");
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again!");
            e.printStackTrace();
        }
    }
}
