package com.foodile.controller;

import com.foodile.exceptions.CustomerExistsException;
import com.foodile.exceptions.CustomerNotFoundException;
import com.foodile.model.Customers;
import com.foodile.service.CustomerServiceImpl;

import java.util.List;

public class CustomerController {
    private final CustomerServiceImpl customerService;

    public CustomerController(CustomerServiceImpl customerService) {
        this.customerService = customerService;
    }

    public Customers save(Customers customer) throws CustomerExistsException {
        return this.customerService.save(customer);
    }

    public Customers validateCustomerLogin(String email, String password) throws CustomerNotFoundException {
        Customers customer = this.customerService.validateCustomerLogin(email, password);
        if(customer != null)
            this.customerService.setCurrentLoggedInCustomer(customer);
        return customer;
    }

    public Customers currentLoggedInCustomer(){
        return this.customerService.getCurrentLoggedInCustomer();
    }

    public Customers getCustomerById(String id) throws CustomerNotFoundException{
        return this.customerService.getCustomerById(id);
    }

    public List<Customers> getCustomersList(){ return this.customerService.getAllCustomers();}

    public Customers updateCustomer(Customers customer) throws CustomerNotFoundException {
        return this.customerService.updateCustomer(customer);
    }

    public void deleteCustomer(String id) throws CustomerNotFoundException{
        this.customerService.deleteCustomer(id);
    }
}