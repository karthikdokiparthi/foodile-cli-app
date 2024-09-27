package com.foodile.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Order {
    private String id;
    private Customers customer;
    private Restaurants restaurant;
    private List<Dish> dishList = new ArrayList<>(); // Initialize to avoid null
    private double totalPrice;
    private Date orderDate;

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Customers getCustomer() { return customer; }
    public void setCustomer(Customers customer) { this.customer = customer; }

    public Restaurants getRestaurant() { return restaurant; }
    public void setRestaurant(Restaurants restaurant) { this.restaurant = restaurant; }

    public List<Dish> getDishList() { return dishList; }
    public void setDishList(List<Dish> dishList) { this.dishList = dishList; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public Date getOrderDate() { return orderDate; }
    public void setOrderDate(Date orderDate) { this.orderDate = orderDate; }

    // Method to calculate total price based on the ordered dishes
    public void calculateTotalPrice() {
        this.totalPrice = dishList.stream().mapToDouble(Dish::getPrice).sum();
    }
}
