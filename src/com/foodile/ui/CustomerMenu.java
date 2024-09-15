package com.foodile.ui;

import com.foodile.controller.CustomerController;
import com.foodile.exceptions.CustomerExistsException;
import com.foodile.exceptions.CustomerNotFoundException;
import com.foodile.model.Customers;
import com.foodile.repository.CustomerRepository;
import com.foodile.service.CustomerServiceImpl;

import java.util.List;
import java.util.Scanner;

public class CustomerMenu extends Menu {
    private final CustomerController customerController;

    public CustomerMenu() {
        // Directly initialize CustomerRepository and CustomerServiceImpl
        CustomerRepository customerRepository = new CustomerRepository();
        CustomerServiceImpl customerService = new CustomerServiceImpl(customerRepository);
        this.customerController = new CustomerController(customerService);
    }

    @Override
    public void displayMenu() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                displayMenuHeader("WELCOME TO CUSTOMER SECTION");
                System.out.println();
                System.out.println("Please select the option !");
                System.out.println("--------------------------");
                System.out.println("1. Register (New Customer)");
                System.out.println("2. Login  (Existing Customer)");
                System.out.println("3. Search Customer");
                System.out.println("4. Display All Customers ");
                System.out.println("5. Update Customer");
                System.out.println("6. Delete Customer");
                System.out.println("7. Exit");

                System.out.println("Please enter your choice (1-7)");

                int input = scanner.nextInt();
                switch (input) {
                    case 1 -> customerRegisterForm();
                    case 2 -> customerLoginForm();
                    case 3 -> customerSearchForm();
                    case 4 -> displayAllCustomers();
                    case 5 -> customerUpdateForm();
                    case 6 -> deleteCustomerForm();
                    case 7 -> {
                        System.out.println("Thank you , See you again !");
                        super.displayMenu();
                    }
                    default -> System.out.println("Invalid Input. Please enter the valid input from(1-7)");

                }
            }
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again !");
            displayMenu();
        }
    }

    public void deleteCustomerForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the following details to delete the Customer\n");
            System.out.println("Enter Id");
            String id = scanner.nextLine();
            customerController.deleteCustomer(id);
            System.out.println("Customer Deleted Successfully");
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
            displayMenu();
        }
    }

    public void customerUpdateForm() {

        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please Update entering the following details\n");
            System.out.println("Enter Your Id");
            String id = scanner.nextLine();
            System.out.println("Enter Name");
            String name = scanner.nextLine();
            System.out.println("Enter E-mail");
            String email = scanner.nextLine();
            System.out.println("Enter Password");
            String password = scanner.nextLine();
            Customers customer = new Customers();
            customer.setId(id)
                    .setName(name)
                    .setEmail(email)
                    .setPassword(password);

            Customers updatedCustomer = customerController.updateCustomer(customer);
            System.out.println("Customer Updated Successfully");
            displayCustomerDetails(updatedCustomer);

        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again !");
            customerUpdateForm();
        }
    }

    public void displayAllCustomers() {
        List<Customers> customersList = this.customerController.getCustomersList();
        String dashesLine = new String(new char[150]).replace('\0', '-');
        displayMenuHeader("Customers");
        System.out.printf("%-10s %-30s %-80s %-30s\n", "Id", "Name", "E-mail", "Password");
        System.out.println(dashesLine);
        customersList.forEach(customer -> {
            System.out.printf("%-10s %-30s %-80s %-30s\n", customer.getId(), customer.getName(), customer.getEmail(), "*".repeat(customer.getPassword().length()));
        });
    }

    public void customerSearchForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please enter the following details to search for Customer\n");
            System.out.println("Enter Id");
            String id = scanner.nextLine();
            Customers customer = customerController.getCustomerById(id);
            displayCustomerDetails(customer);
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
            displayMenu();
        }
    }

    public void customerLoginForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please login entering the following details\n");
            System.out.println("Enter E-mail");
            String email = scanner.nextLine();
            System.out.println("Enter Password");
            String password = scanner.nextLine();
            Customers existingCustomer = customerController.validateCustomerLogin(email, password);
            System.out.println("Login Success :");
            System.out.println("Welcome Mr : " + existingCustomer.getName());
        } catch (CustomerNotFoundException e) {
            System.out.println(e.getMessage());
            displayMenu();
        }
    }

    public void customerRegisterForm() {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Please register entering the following details\n");
            System.out.println("Enter Id");
            String id = scanner.nextLine();
            System.out.println("Enter Name");
            String name = scanner.nextLine();
            System.out.println("Enter E-mail");
            String email = scanner.nextLine();
            System.out.println("Enter Password");
            String password = scanner.nextLine();
            Customers customer = new Customers();
            customer.setId(id)
                    .setName(name)
                    .setEmail(email)
                    .setPassword(password);

            Customers savedCustomer = customerController.save(customer);
            System.out.println("Customer Registration Successful");
            displayCustomerDetails(savedCustomer);
        } catch (CustomerExistsException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("Some internal error occurred. Please try again !");
            customerRegisterForm();
        }

    }

    public void displayCustomerDetails(Customers customer) {
        displayMenuHeader("Customer Details");
        System.out.printf("%-10s %-30s %-80s %-30s\n", "Id", "Name", "E-mail", "Password");
        printDashLine();
        System.out.printf("%-10s %-30s %-80s %-30s\n", customer.getId(), customer.getName(), customer.getEmail(), "*".repeat(customer.getPassword().length()));

    }
}
