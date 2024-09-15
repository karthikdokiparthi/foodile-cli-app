package com.foodile.service;

import com.foodile.exceptions.CustomerExistsException;
import com.foodile.exceptions.CustomerNotFoundException;
import com.foodile.model.Customers;

import java.util.List;

public interface CustomerService {
    public Customers save(Customers customer) throws CustomerExistsException;

    public List<Customers> getAllCustomers();

    public Customers getCustomerById(String id) throws CustomerNotFoundException;

    public Customers updateCustomer(Customers customer) throws CustomerNotFoundException;

    public void deleteCustomer(String id) throws CustomerNotFoundException;

    public Customers validateCustomerLogin(String email, String password) throws CustomerNotFoundException;

    public void setCurrentLoggedInCustomer(Customers customer);

    public Customers getCurrentLoggedInCustomer();
}