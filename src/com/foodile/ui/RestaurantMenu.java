package com.foodile.ui;

import com.foodile.model.Dish;
import com.foodile.model.Restaurants;
import com.foodile.exceptions.RestaurantNotFoundException;
import com.foodile.exceptions.RestaurantExistsException;
import com.foodile.repository.RestaurantRepository;
import com.foodile.service.DishService;
import com.foodile.service.DishServiceImpl;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class RestaurantMenu extends Menu {

    private RestaurantRepository restaurantRepository;
    private DishServiceImpl dishService;

    public RestaurantMenu() {
        this.restaurantRepository = new RestaurantRepository(); // Initialize the repository
    }

    @Override
    public void displayMenu() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                displayMenuHeader("WELCOME TO RESTAURANT SECTION");
                System.out.println();
                System.out.println("Please select the option!");
                System.out.println("--------------------------");
                System.out.println("1. Add New Restaurant");
                System.out.println("2. View All Restaurants");
                System.out.println("3. Search Restaurant");
                System.out.println("4. Update Restaurant");
                System.out.println("5. Delete Restaurant");
                System.out.println("6. Exit");

                System.out.println("Please enter your choice (1-6)");

                int input = scanner.nextInt();
                switch (input) {
                    case 1 -> newRestaurantForm();
                    case 2 -> displayRestaurants();
                    case 3 -> restaurantSearchForm();
                    case 4 -> restaurantUpdateForm();
                    case 5 -> restaurantDeleteForm();
                    case 6 -> {
                        System.out.println("Thank you, See you again!");
                        super.displayMenu();
                    }
                    default -> System.out.println("Invalid Input. Please enter a valid input (1-6)");
                }
            }
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again!");
            displayMenu();
        }
    }

    public void restaurantDeleteForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the following details to delete the Restaurant\n");
            System.out.println("Enter Id");
            String id = scanner.nextLine();
            Optional<Restaurants> restaurantOpt = restaurantRepository.findRestaurantById(id);
            if (restaurantOpt.isPresent()) {
                restaurantRepository.deleteRestaurant(restaurantOpt.get());
                System.out.println("Restaurant Deleted Successfully");
            } else {
                throw new RestaurantNotFoundException("Restaurant with ID " + id + " not found.");
            }
        } catch (RestaurantNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again!");
            restaurantDeleteForm();
        }
    }

    public void restaurantUpdateForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the following details to update the Restaurant\n");
            System.out.println("Enter Restaurant Id");
            String id = scanner.nextLine();
            System.out.println("Enter Name");
            String name = scanner.nextLine();
            System.out.println("Enter Address");
            String address = scanner.nextLine();
            System.out.println("Enter Menu Dish Items separated by : (D101:D102)");
            String menu = scanner.nextLine();

            Optional<Restaurants> restaurantOpt = restaurantRepository.findRestaurantById(id);
            if (restaurantOpt.isPresent()) {
                Restaurants restaurant = restaurantOpt.get();
                restaurant.setName(name);
                restaurant.setAddress(address);
                restaurant.setMenu(Arrays.asList(menu.split(":")));

                restaurantRepository.updateRestaurant(restaurant);
                System.out.println("Restaurant Updated Successfully");
                displayRestaurant(restaurant);
            } else {
                throw new RestaurantNotFoundException("Restaurant with ID " + id + " not found.");
            }
        } catch (RestaurantNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again!");
            restaurantUpdateForm();
        }
    }

    public void restaurantSearchForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the following details to search for Restaurant\n");
            System.out.println("Enter Id");
            String id = scanner.nextLine();
            Optional<Restaurants> restaurantOpt = restaurantRepository.findRestaurantById(id);
            if (restaurantOpt.isPresent()) {
                displayRestaurant(restaurantOpt.get());
            } else {
                throw new RestaurantNotFoundException("Restaurant with ID " + id + " not found.");
            }
        } catch (RestaurantNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void newRestaurantForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the following details\n");
            System.out.println("Enter Id");
            String id = scanner.nextLine();
            System.out.println("Enter Name");
            String name = scanner.nextLine();
            System.out.println("Enter Address");
            String address = scanner.nextLine();
            System.out.println("Enter Dishes for Menu separated by : (D010:D009)");
            String menu = scanner.nextLine();

            Optional<Restaurants> restaurantOpt = restaurantRepository.findRestaurantById(id);
            if (restaurantOpt.isEmpty()) {
                Restaurants restaurant = new Restaurants();
                restaurant.setId(id);
                restaurant.setName(name);
                restaurant.setAddress(address);
                restaurant.setMenu(Arrays.asList(menu.split(":")));
                restaurantRepository.save(restaurant);
                displayRestaurant(restaurant);
            } else {
                throw new RestaurantExistsException("Restaurant with ID " + id + " already exists.");
            }
        } catch (RestaurantExistsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again!");
            newRestaurantForm();
        }
    }

    public void displayRestaurants() {
        List<Restaurants> restaurantList = restaurantRepository.getRestaurantList();
        displayMenuHeader("Restaurants");
        System.out.printf("%-10s %-30s %-80s %-30s\n", "Id", "Name", "Address", "Menu Items");
        printDashLine();
        restaurantList.forEach(restaurant -> {
            System.out.printf("%-10s %-30s %-80s %-30s\n", restaurant.getId(), restaurant.getName(), restaurant.getAddress(), String.join(":", restaurant.getMenu()));
        });
    }

    public void displayRestaurant(Restaurants restaurant) {
        displayMenuHeader("Restaurant Details");
        System.out.printf("%-10s %-30s %-80s %-30s\n", "Id", "Name", "Address", "Menu Items");
        printDashLine();
        System.out.printf("%-10s %-30s %-80s %-30s\n", restaurant.getId(), restaurant.getName(), restaurant.getAddress(), String.join(":", restaurant.getMenu()));
    }

    public RestaurantMenu(DishServiceImpl dishService) {
        this.dishService = dishService;
    }

    public void displayMenuItems(String restaurantId) {
        try {
            List<Dish> dishList = dishService.getDishesByRestaurantId(restaurantId);  // Assuming this method exists
            System.out.printf("%-10s %-30s %-60s %-10s\n", "Dish ID", "Name", "Description", "Price");
            dishList.forEach(dish -> System.out.printf("%-10s %-30s %-60s %-10.2f\n", dish.getId(), dish.getName(), dish.getDescription(), dish.getPrice()));
        } catch (Exception e) {
            System.out.println("Error fetching menu items: " + e.getMessage());
        }
    }
}
