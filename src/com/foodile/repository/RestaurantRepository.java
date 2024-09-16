package com.foodile.repository;

import com.foodile.model.Restaurants;
import com.foodile.exceptions.RestaurantNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RestaurantRepository {

    private final String DB_URL = "jdbc:postgresql://localhost:5432/foodileapp";  // Replace with your DB URL
    private final String USER = "postgres"; // Replace with your DB username
    private final String PASS = "K@rthik"; // Replace with your DB password

    public RestaurantRepository() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("PostgreSQL Driver not found.");
        }
    }

    // Fetch all restaurants
    public List<Restaurants> getRestaurantList() {
        List<Restaurants> restaurantsList = new ArrayList<>();
        String query = "SELECT * FROM restaurants";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Restaurants restaurant = new Restaurants();
                restaurant.setId(rs.getString("id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setMenu(List.of(rs.getString("menu").split(":")));
                restaurantsList.add(restaurant);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurantsList;
    }

    // Save a new restaurant
    public Restaurants save(Restaurants restaurant) {
        String query = "INSERT INTO restaurants (id, name, address, menu) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, restaurant.getId());
            pstmt.setString(2, restaurant.getName());
            pstmt.setString(3, restaurant.getAddress());
            pstmt.setString(4, String.join(":", restaurant.getMenu()));

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    // Find restaurant by ID
    public Optional<Restaurants> findRestaurantById(String id) {
        String query = "SELECT * FROM restaurants WHERE id = ?";
        Restaurants restaurant = null;

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                restaurant = new Restaurants();
                restaurant.setId(rs.getString("id"));
                restaurant.setName(rs.getString("name"));
                restaurant.setAddress(rs.getString("address"));
                restaurant.setMenu(List.of(rs.getString("menu").split(":")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(restaurant);
    }

    // Update restaurant
    public Restaurants updateRestaurant(Restaurants restaurant) {
        String query = "UPDATE restaurants SET name = ?, address = ?, menu = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, restaurant.getName());
            pstmt.setString(2, restaurant.getAddress());
            pstmt.setString(3, String.join(":", restaurant.getMenu()));
            pstmt.setString(4, restaurant.getId());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return restaurant;
    }

    // Delete restaurant
    public void deleteRestaurant(Restaurants restaurant) {
        String query = "DELETE FROM restaurants WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, restaurant.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
