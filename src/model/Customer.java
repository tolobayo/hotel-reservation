package model;

import java.util.regex.Pattern;
import java.util.Objects;


public class Customer {
    private String firstName;
    private String lastName;
    private String email;

    public Customer(String firstName, String lastName, String email) {
        
            String emailRegex = "^(.+)@(.+).(.+)$";
            Pattern pattern = Pattern.compile(emailRegex);

            if (!(pattern.matcher(email).matches())) throw new IllegalArgumentException("Invalid email input.");

        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public final String getFirstName() {
        return firstName;
    }

    public final void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public final String getLastName() {
        return lastName;
    }

    public final void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public final String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Customer)) return false;
        Customer customer =  (Customer) o;
        return email.equals(customer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }


    @Override
    public String toString() {
        return "Customer [firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + "]";
    }
    

}
