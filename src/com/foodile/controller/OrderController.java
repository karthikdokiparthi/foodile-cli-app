package com.foodile.controller;

import com.foodile.model.Order;
import com.foodile.service.OrderService;

import java.util.List;

public class OrderController {

    private final OrderService orderService;

    // Constructor for OrderController
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    // Method to create an order
    public void createOrder(Order order) {
        try {
            orderService.createOrder(order);
            System.out.println("Order created successfully!");
        } catch (Exception e) {
            System.err.println("Error creating order: " + e.getMessage());
        }
    }

    // Method to get an order by ID
    public Order getOrderById(String id) {
        try {
            Order order = orderService.getOrderById(id);
            if (order != null) {
                return order;
            } else {
                System.out.println("Order with ID " + id + " not found.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error fetching order: " + e.getMessage());
            return null;
        }
    }

    // Method to get all orders
    public List<Order> getAllOrders() {
        try {
            return orderService.getAllOrders();
        } catch (Exception e) {
            System.err.println("Error fetching orders: " + e.getMessage());
            return List.of(); // Return an empty list on error
        }
    }

    // Method to update an order
    public void updateOrder(String id, Order updatedOrder) {
        try {
            orderService.updateOrder(id, updatedOrder);
            System.out.println("Order updated successfully!");
        } catch (Exception e) {
            System.err.println("Error updating order: " + e.getMessage());
        }
    }

    // Method to delete an order
    public void deleteOrder(String id) {
        try {
            orderService.deleteOrder(id);
            System.out.println("Order deleted successfully!");
        } catch (Exception e) {
            System.err.println("Error deleting order: " + e.getMessage());
        }
    }
    public void handleCreateOrderRequest(Order order) {
        try {
            // Validate order object
            if (order == null) {
                throw new IllegalArgumentException("Order cannot be null");
            }
            if (order.getDishList() == null || order.getDishList().isEmpty()) {
                throw new IllegalArgumentException("Dish list cannot be null or empty");
            }

            // Additional validations if needed
            if (order.getCustomer() == null || order.getRestaurant() == null) {
                throw new IllegalArgumentException("Customer and Restaurant cannot be null");
            }

            // Call the service layer to place the order
            orderService.placeOrder(order);

            // Respond with success message
            System.out.println("Order placed successfully");

        } catch (IllegalArgumentException e) {
            // Handle specific validation errors
            System.err.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            // Handle unexpected errors
            System.err.println("An error occurred while placing the order: " + e.getMessage());
        }
    }
}
