package com.foodile.service;

import com.foodile.exceptions.CustomerExistsException;
import com.foodile.exceptions.CustomerNotFoundException;
import com.foodile.model.Customers;
import com.foodile.repository.CustomerRepository;

import java.util.List;
import java.util.Optional;


public class CustomerServiceImpl implements CustomerService{

    private final CustomerRepository customerRepository;
    private Customers currentLoggedInCustomer;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Customers save(Customers customer) throws CustomerExistsException {
        Optional<Customers> customerById = this.customerRepository.findCustomerById(customer.getId());
        if(customerById.isPresent())
            throw new CustomerExistsException("Customer Already Exists with this Id  :" + customer.getId());
        return this.customerRepository.saveCustomer(customer);
    }

    @Override
    public List<Customers> getAllCustomers() {
        return this.customerRepository.getCustomersList();
    }

    @Override
    public Customers getCustomerById(String id) throws CustomerNotFoundException {
        Optional<Customers> customerById = this.customerRepository.findCustomerById(id);
        if(customerById.isEmpty())
            throw new CustomerNotFoundException("Customer Not Found with Id : " + id);
        return customerById.get();
    }

    @Override
    public Customers updateCustomer(Customers customer) throws CustomerNotFoundException {
        Optional<Customers> customerById = this.customerRepository.findCustomerById(customer.getId());
        if(customerById.isEmpty())
            throw new CustomerNotFoundException("Customer Not Found with Id : " + customer.getId());
        return this.customerRepository.updateCustomer(customer);
    }

    @Override
    public void deleteCustomer(String id) throws CustomerNotFoundException {
        Optional<Customers> customerById = this.customerRepository.findCustomerById(id);
        if(customerById.isEmpty())
            throw new CustomerNotFoundException("Customer Not Found with Id : " + id);
        this.customerRepository.deleteCustomer(customerById.get());
    }

    @Override
    public Customers validateCustomerLogin(String email, String password) throws CustomerNotFoundException {
        Optional<Customers> customerById = this.customerRepository.findByEmailAndPassword(email,password);
        if(customerById.isEmpty())
            throw new CustomerNotFoundException("Invalid Email or Password");
        return customerById.get();
    }

    @Override
    public void setCurrentLoggedInCustomer(Customers customer) {
        this.currentLoggedInCustomer = customer;
    }

    @Override
    public Customers getCurrentLoggedInCustomer() {
        return this.currentLoggedInCustomer;
    }

}