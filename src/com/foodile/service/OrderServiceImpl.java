package com.foodile.service;

import com.foodile.model.Order;
import com.foodile.repository.OrderRepository;

import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    // Constructor injection for OrderRepository
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    // Create a new order
    @Override
    public void createOrder(Order order) {
        if (order == null || order.getDishList() == null || order.getDishList().isEmpty()) {
            throw new IllegalArgumentException("Order or dish list cannot be null or empty");
        }

        // Calculate total price based on dishes
        order.calculateTotalPrice();

        // Create the order in the repository
        orderRepository.createOrder(order);
    }
    // Get order by ID
    @Override
    public Order getOrderById(String id) {
        Optional<Order> order = Optional.ofNullable(orderRepository.getOrderById(id));
        return order.orElseThrow(() -> new IllegalArgumentException("Order not found for ID: " + id));
    }

    // Get all orders
    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.getAllOrders();
        if (orders == null || orders.isEmpty()) {
            System.out.println("No orders found.");
        }
        return orders;
    }

    // Update an existing order by ID
    @Override
    public void updateOrder(String id, Order updatedOrder) {
        if (updatedOrder == null || updatedOrder.getDishList() == null || updatedOrder.getDishList().isEmpty()) {
            throw new IllegalArgumentException("Updated order or dish list cannot be null or empty");
        }
        // Recalculating total price for the updated order
        updatedOrder.calculateTotalPrice();
        orderRepository.updateOrder(id, updatedOrder);
    }

    // Delete an order by ID
    @Override
    public void deleteOrder(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("Order ID cannot be null or empty");
        }
        orderRepository.deleteOrder(id);
    }

    public void placeOrder(Order order) {
        // Perform validation
        if (order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        if (order.getDishList() == null || order.getDishList().isEmpty()) {
            throw new IllegalArgumentException("Dish list cannot be null or empty");
        }

        // Ensure the total price is calculated
        order.calculateTotalPrice();

        // Create the order in the repository
        orderRepository.createOrder(order);
    }

}

