package com.foodile.model;

public class Dish {
    private String id;
    private String name;
    private String description;
    private double price;

    public Dish() {
    }

    public Dish(String id, String name, String description, double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public Dish setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Dish setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public Dish setDescription(String description) {
        this.description = description;
        return this;
    }

    public double getPrice() {
        return price;
    }

    public Dish setPrice(double price) {
        this.price = price;
        return this;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                '}';
    }
}
