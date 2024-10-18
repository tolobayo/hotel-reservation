package service;

import model.Customer;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;


public class CustomerService {
    private static CustomerService reference = new CustomerService();
    private Map<String, Customer> customerList;
    private CustomerService() {
        this.customerList = new HashMap<String, Customer>();
    }

    public static CustomerService getInstance() {
        return reference;
    }

    public void addCustomer(String email, String firstName, String lastName) {
        //TODO: Check for duplicates
        Customer newCustomer = new Customer(firstName, lastName, email);
        customerList.put(email, newCustomer);
    }

    public Customer getCustomer(String customerEmail) {
        return customerList.get(customerEmail);
    } 

    public Collection<Customer> getAllCustomers() {
        return customerList.values();
    }
}
