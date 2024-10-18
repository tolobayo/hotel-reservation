package api;

import java.util.List;
import java.util.Collection;

import model.Customer;
import model.IRoom;
import service.CustomerService;
import service.ReservationService;

public class AdminResource {
    private static AdminResource reference = new AdminResource();

    private AdminResource() {}

    public static AdminResource getInstance() {
        return reference;
    }

    public Customer getCustomer(String email) {
        CustomerService customerService = CustomerService.getInstance();
        return customerService.getCustomer(email);
    }
    public void addRoom(List<IRoom> rooms) {
        ReservationService reservationService = ReservationService.getInstance();
        
        for (IRoom room : rooms) {
            reservationService.addRoom(room);
        }
    }
    public Collection<IRoom> getAllRooms() {
        ReservationService reservationService = ReservationService.getInstance();
        return reservationService.getAllRooms();

    }
    public Collection<Customer> getAllCustomers() {
        CustomerService customerService = CustomerService.getInstance();
        return customerService.getAllCustomers();
    }
    public void displayAllReservations() {
        ReservationService reservationService = ReservationService.getInstance();
        reservationService.printAllReservation();
    }

}
