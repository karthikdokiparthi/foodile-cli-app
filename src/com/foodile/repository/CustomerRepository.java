package com.foodile.repository;

import com.foodile.model.Customers;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CustomerRepository {
    private final String url = "jdbc:postgresql://localhost:5432/foodileapp";  // Replace with your DB URL
    private final String username = "postgres"; // Replace with your DB username
    private final String password = "K@rthik"; // Replace with your DB password

    public CustomerRepository() {
        // Load PostgreSQL driver (optional in recent versions)
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    public List<Customers> getCustomersList() {
        List<Customers> customersList = new ArrayList<>();
        String query = "SELECT * FROM customers";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customers customer = new Customers();
                customer.setId(rs.getString("id"));
                customer.setName(rs.getString("name"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customersList.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customersList;
    }

    public Customers saveCustomer(Customers customer) {
        String query = "INSERT INTO customers (id, name, email, password) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getId());
            pstmt.setString(2, customer.getName());
            pstmt.setString(3, customer.getEmail());
            pstmt.setString(4, customer.getPassword());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return customer;
    }

    public Optional<Customers> findCustomerById(String id) {
        String query = "SELECT * FROM customers WHERE id = ?";
        Customers customer = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();

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

        return Optional.ofNullable(customer);
    }

    public Optional<Customers> findCustomerByEmail(String email) {
        String query = "SELECT * FROM customers WHERE email = ?";
        Customers customer = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

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

        return Optional.ofNullable(customer);
    }

    public Customers updateCustomer(Customers customerToBeUpdated) {
        String query = "UPDATE customers SET name = ?, email = ?, password = ? WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customerToBeUpdated.getName());
            pstmt.setString(2, customerToBeUpdated.getEmail());
            pstmt.setString(3, customerToBeUpdated.getPassword());
            pstmt.setString(4, customerToBeUpdated.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return customerToBeUpdated;
    }

    public void deleteCustomer(Customers customer) {
        String query = "DELETE FROM customers WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Customers> findByEmailAndPassword(String email, String password) {
        String query = "SELECT * FROM customers WHERE email = ? AND password = ?";
        Customers customer = null;

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

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

        return Optional.ofNullable(customer);
    }
}
