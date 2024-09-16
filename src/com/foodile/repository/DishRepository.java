package com.foodile.repository;

import com.foodile.model.Dish;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DishRepository {
    private final String URL = "jdbc:postgresql://localhost:5432/foodileapp";  // Replace with your DB URL
    private final String USER = "postgres"; // Replace with your DB username
    private final String PASSWORD = "K@rthik"; // Replace with your DB password

    public List<Dish> getDishList() {
        List<Dish> dishList = new ArrayList<>();
        String query = "SELECT id, name, price, description FROM dishes";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Dish dish = new Dish()
                        .setId(rs.getString("id"))
                        .setName(rs.getString("name"))
                        .setPrice(rs.getDouble("price"))
                        .setDescription(rs.getString("description"));
                dishList.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dishList;
    }

    public Dish saveDish(Dish dish) {
        String query = "INSERT INTO dishes (id, name, price, description) VALUES (?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, dish.getId());
            pstmt.setString(2, dish.getName());
            pstmt.setDouble(3, dish.getPrice());
            pstmt.setString(4, dish.getDescription());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dish;
    }

    public Optional<Dish> findDishById(String id) {
        String query = "SELECT id, name, price, description FROM dishes WHERE id = ?";
        Dish dish = null;

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                dish = new Dish()
                        .setId(rs.getString("id"))
                        .setName(rs.getString("name"))
                        .setPrice(rs.getDouble("price"))
                        .setDescription(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(dish);
    }

    public Dish updateDish(Dish dishToBeUpdated) {
        String query = "UPDATE dishes SET name = ?, price = ?, description = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, dishToBeUpdated.getName());
            pstmt.setDouble(2, dishToBeUpdated.getPrice());
            pstmt.setString(3, dishToBeUpdated.getDescription());
            pstmt.setString(4, dishToBeUpdated.getId());
            int rowsUpdated = pstmt.executeUpdate();

            if (rowsUpdated > 0) {
                return dishToBeUpdated;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteDish(Dish dish) {
        String query = "DELETE FROM dishes WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, dish.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
