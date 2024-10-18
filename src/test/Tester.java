package test;

import model.*;

public class Tester {
    public static void main(String[] args) {
        Customer customer = new Customer("Joe", "Lemon", "j@domain.com");
        Customer invalidCustomer = new Customer("Joe", "Lemon", "@domain.com");
        System.out.println(customer);
        System.out.println(invalidCustomer);
    }

}
