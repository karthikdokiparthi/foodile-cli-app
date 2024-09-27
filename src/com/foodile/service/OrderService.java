package com.foodile.service;

import com.foodile.model.Order;

import java.util.List;

public interface OrderService {

    // Method to create a new order
    void createOrder(Order order);

    // Method to get an order by ID
    Order getOrderById(String id);

    // Method to get all orders
    List<Order> getAllOrders();

    // Method to update an order
    void updateOrder(String id, Order updatedOrder);
    // Method to delete an order by ID
    void deleteOrder(String id);

    void placeOrder(Order order);
}
