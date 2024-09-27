package com.foodile.ui;

import com.foodile.controller.OrderController;
import com.foodile.model.Order;

import java.util.List;
import java.util.Scanner;

public class OrderMenu extends Menu {
    private final OrderController orderController;

    // Constructor
    public OrderMenu(OrderController orderController) {
        this.orderController = orderController;
    }

    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- ORDER MANAGEMENT ---");
            System.out.println("1. Create Order");
            System.out.println("2. View Order by ID");
            System.out.println("3. View All Orders");
            System.out.println("4. Update Order");
            System.out.println("5. Delete Order");
            System.out.println("6. Back to Main Menu");
            System.out.print("Enter your choice (1-6): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> createOrder(scanner);
                case 2 -> viewOrderById(scanner);
                case 3 -> viewAllOrders();
                case 4 -> updateOrder(scanner);
                case 5 -> deleteOrder(scanner);
                case 6 -> {
                    System.out.println("Returning to Main Menu...");
                    return; // Exit the loop to return to the main menu
                }
                default -> System.out.println("Invalid choice, please enter a number between 1 and 6.");
            }
        }
    }

    private void createOrder(Scanner scanner) {
        try {
            // Collecting Order Details from User
            System.out.print("Enter Order ID: ");
            String id = scanner.nextLine();

            // You will need to gather other details like customer, restaurant, dishList, etc.
            // For now, using placeholder data
            Order order = new Order();
            order.setId(id);
            // You would normally fetch or create these objects
            // order.setCustomer(new Customers("customerId"));
            // order.setRestaurant(new Restaurants("restaurantId"));
            // order.setDishList(fetchDishes()); // This method needs to be implemented to fetch dishes

            // Calculate total price and create order
            order.calculateTotalPrice();
            orderController.createOrder(order);
            System.out.println("Order created successfully!");
        } catch (Exception e) {
            System.err.println("Error creating order: " + e.getMessage());
        }
    }

    private void viewOrderById(Scanner scanner) {
        try {
            System.out.print("Enter Order ID to view: ");
            String id = scanner.nextLine();
            Order order = orderController.getOrderById(id);
            if (order != null) {
                System.out.println("Order Details: " + order);
            } else {
                System.out.println("Order with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.err.println("Error fetching order: " + e.getMessage());
        }
    }

    private void viewAllOrders() {
        try {
            List<Order> orders = orderController.getAllOrders();
            if (!orders.isEmpty()) {
                orders.forEach(System.out::println);
            } else {
                System.out.println("No orders found.");
            }
        } catch (Exception e) {
            System.err.println("Error fetching orders: " + e.getMessage());
        }
    }

    private void updateOrder(Scanner scanner) {
        try {
            System.out.print("Enter Order ID to update: ");
            String id = scanner.nextLine();
            // Fetch the existing order
            Order existingOrder = orderController.getOrderById(id);
            if (existingOrder != null) {
                // Modify the existing order
                // For now, using placeholder modifications
                Order updatedOrder = new Order();
                updatedOrder.setId(id);
                // Set other details here

                // Update the order
                orderController.updateOrder(id, updatedOrder);
                System.out.println("Order updated successfully!");
            } else {
                System.out.println("Order with ID " + id + " not found.");
            }
        } catch (Exception e) {
            System.err.println("Error updating order: " + e.getMessage());
        }
    }

    private void deleteOrder(Scanner scanner) {
        try {
            System.out.print("Enter Order ID to delete: ");
            String id = scanner.nextLine();
            orderController.deleteOrder(id);
            System.out.println("Order deleted successfully!");
        } catch (Exception e) {
            System.err.println("Error deleting order: " + e.getMessage());
        }
    }
}
