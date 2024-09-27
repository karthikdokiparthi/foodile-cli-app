package com.foodile.repository;

import com.foodile.model.Order;
import com.foodile.model.Customers;
import com.foodile.model.Dish;
import com.foodile.model.Restaurants;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderRepository {

    private Connection connection;

    // Constructor to establish the database connection
    public OrderRepository() {
        try {
            // Load the PostgreSQL driver
            Class.forName("org.postgresql.Driver");
            // Establish connection to the database (update the URL, user, and password)
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/foodileapp", "postgres", "K@rthik");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to create a new order in the database
    public void createOrder(Order order) {
        if (order == null || order.getDishList() == null || order.getDishList().isEmpty()) {
            throw new IllegalArgumentException("Order or dish list cannot be null or empty");
        }

        String query = "INSERT INTO orders (id, customer_id, restaurant_id, total_price, order_date) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, order.getId());
            stmt.setString(2, order.getCustomer().getId());
            stmt.setString(3, order.getRestaurant().getId());
            stmt.setDouble(4, order.getTotalPrice());
            stmt.setDate(5, new java.sql.Date(order.getOrderDate().getTime()));

            stmt.executeUpdate();

            // Insert dishes associated with the order
            String dishQuery = "INSERT INTO order_dishes (order_id, dish_id) VALUES (?, ?)";
            try (PreparedStatement dishStmt = connection.prepareStatement(dishQuery)) {
                for (Dish dish : order.getDishList()) {
                    dishStmt.setString(1, order.getId());
                    dishStmt.setString(2, dish.getId());
                    dishStmt.addBatch();  // Add to batch for multiple inserts
                }
                dishStmt.executeBatch();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while creating order", e);
        }
    }


    // In the getOrderById method
    public Order getOrderById(String id) {
        String query = "SELECT * FROM orders WHERE id = ?";
        Order order = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Fetch customer and restaurant details
                Customers customer = getCustomerById(rs.getString("customer_id"));
                Restaurants restaurant = getRestaurantById(rs.getString("restaurant_id"));

                // Fetch the dishes associated with the order
                List<Dish> dishList = getDishesByOrderId(id);
                if (dishList == null) {
                    dishList = new ArrayList<>(); // Ensure it's not null
                }

                // Create the Order object
                order = new Order();
                order.setId(rs.getString("id"));
                order.setCustomer(customer);
                order.setRestaurant(restaurant);
                order.setDishList(dishList);
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setOrderDate(rs.getDate("order_date"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return order;
    }

    // Helper method to get a customer by ID
    private Customers getCustomerById(String customerId) {
        String query = "SELECT * FROM customers WHERE id = ?";
        Customers customer = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                customer = new Customers();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    // Helper method to get a restaurant by ID
    private Restaurants getRestaurantById(String restaurantId) {
        String query = "SELECT * FROM restaurants WHERE id = ?";
        Restaurants restaurant = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, restaurantId);
            ResultSet rs = stmt.executeQuery();

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

        return restaurant;
    }

    // Helper method to get all dishes associated with an order
    private List<Dish> getDishesByOrderId(String orderId) {
        List<Dish> dishList = new ArrayList<>();
        String query = "SELECT d.id, d.name, d.price, d.description FROM dishes d " +
                "INNER JOIN order_dishes od ON d.id = od.dish_id WHERE od.order_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Dish dish = new Dish();
                dish.setId(rs.getString("id"));
                dish.setName(rs.getString("name"));
                dish.setPrice(rs.getDouble("price"));
                dish.setDescription(rs.getString("description"));
                dishList.add(dish);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dishList;
    }

    // Method to update an existing order
    public void updateOrder(String id,Order order) {
        String query = "UPDATE orders SET customer_id = ?, restaurant_id = ?, total_price = ?, order_date = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, order.getCustomer().getId());
            stmt.setString(2, order.getRestaurant().getId());
            stmt.setDouble(3, order.getTotalPrice());
            stmt.setDate(4, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setString(5, order.getId());

            stmt.executeUpdate();

            // Update the dishes for the order (simplified for example purposes)
            String deleteOldDishes = "DELETE FROM order_dishes WHERE order_id = ?";
            try (PreparedStatement deleteStmt = connection.prepareStatement(deleteOldDishes)) {
                deleteStmt.setString(1, order.getId());
                deleteStmt.executeUpdate();
            }

            String insertNewDishes = "INSERT INTO order_dishes (order_id, dish_id) VALUES (?, ?)";
            try (PreparedStatement dishStmt = connection.prepareStatement(insertNewDishes)) {
                for (Dish dish : order.getDishList()) {
                    dishStmt.setString(1, order.getId());
                    dishStmt.setString(2, dish.getId());
                    dishStmt.addBatch();
                }
                dishStmt.executeBatch();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete an order
    public void deleteOrder(String orderId) {
        String deleteDishesQuery = "DELETE FROM order_dishes WHERE order_id = ?";
        String deleteOrderQuery = "DELETE FROM orders WHERE id = ?";

        try (PreparedStatement dishStmt = connection.prepareStatement(deleteDishesQuery);
             PreparedStatement orderStmt = connection.prepareStatement(deleteOrderQuery)) {

            dishStmt.setString(1, orderId);
            dishStmt.executeUpdate();

            orderStmt.setString(1, orderId);
            orderStmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Fetch all orders from the database
    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                // Fetch customer, restaurant, and dishes for each order
                Customers customer = getCustomerById(rs.getString("customer_id"));
                Restaurants restaurant = getRestaurantById(rs.getString("restaurant_id"));
                List<Dish> dishList = getDishesByOrderId(rs.getString("id"));

                // Create the Order object
                Order order = new Order();
                order.setId(rs.getString("id"));
                order.setCustomer(customer);
                order.setRestaurant(restaurant);
                order.setDishList(dishList);
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setOrderDate(rs.getDate("order_date"));

                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Fetch all orders by customer ID
    public List<Order> getOrdersByCustomerId(String customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customer_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, customerId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Fetch restaurant and dishes for the order
                Restaurants restaurant = getRestaurantById(rs.getString("restaurant_id"));
                List<Dish> dishList = getDishesByOrderId(rs.getString("id"));

                // Create the Order object
                Order order = new Order();
                order.setId(rs.getString("id"));
                order.setCustomer(getCustomerById(rs.getString("customer_id")));
                order.setRestaurant(restaurant);
                order.setDishList(dishList);
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setOrderDate(rs.getDate("order_date"));

                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    // Fetch all orders by restaurant ID
    public List<Order> getOrdersByRestaurantId(String restaurantId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE restaurant_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, restaurantId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Fetch customer and dishes for the order
                Customers customer = getCustomerById(rs.getString("customer_id"));
                List<Dish> dishList = getDishesByOrderId(rs.getString("id"));

                // Create the Order object
                Order order = new Order();
                order.setId(rs.getString("id"));
                order.setCustomer(customer);
                order.setRestaurant(getRestaurantById(restaurantId));
                order.setDishList(dishList);
                order.setTotalPrice(rs.getDouble("total_price"));
                order.setOrderDate(rs.getDate("order_date"));

                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }
}
