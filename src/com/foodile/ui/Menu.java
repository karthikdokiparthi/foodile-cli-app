package com.foodile.ui;

import com.foodile.controller.DishController;
import com.foodile.controller.OrderController;
import com.foodile.repository.OrderRepository;
import com.foodile.service.DishServiceImpl;
import com.foodile.service.OrderServiceImpl;

import java.util.Scanner;

public class Menu {
    private final DishController dishController;
    private final OrderController orderController;

    // Constructor to initialize both DishController and OrderController
    public Menu(DishController dishController, OrderController orderController) {
        this.dishController = dishController;
        this.orderController = orderController;
    }

    // Default constructor with default implementations
    public Menu() {
        this.dishController = new DishController(new DishServiceImpl());
        this.orderController = new OrderController(new OrderServiceImpl(new OrderRepository()));
    }

    public void displayMenu() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                displayMenuHeader("WELCOME TO FOODIE APP");
                System.out.println();
                System.out.println("Please select the option !");
                System.out.println("--------------------------");
                System.out.println("1. Customer Section");
                System.out.println("2. Restaurant Section");
                System.out.println("3. Dishes Section");
                System.out.println("4. Order Section");
                System.out.println("5. Exit");
                System.out.println("Please enter your choice (1-5)");

                int input = scanner.nextInt();
                switch (input) {
                    case 1 -> new CustomerMenu().displayMenu(); // Ensure CustomerMenu is correctly implemented
                    case 2 -> new RestaurantMenu().displayMenu(); // Ensure RestaurantMenu is correctly implemented
                    case 3 -> new DishMenu(dishController).displayMenu(); // Pass dishController to DishMenu
                    case 4 -> new OrderMenu(orderController).displayMenu(); // Pass orderController to OrderMenu
                    case 5 -> {
                        System.out.println("Thanks for choosing Foodie App, See you again !");
                        System.exit(0);
                    }
                    default -> System.out.println("Invalid Input. Please enter a valid input from (1-5)");
                }
            }
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again !");
            e.printStackTrace();
            displayMenu();
        }
    }

    public void displayMenuHeader(String menuHeader) {
        printDashLine();
        String spaces = new String(new char[70]).replace('\0', ' ');
        System.out.printf("%-70s %-10s %-70s \n", spaces, menuHeader, spaces);
        printDashLine();
    }

    public void printDashLine() {
        String dashesLine = new String(new char[150]).replace('\0', '-');
        System.out.println(dashesLine);
    }
}
