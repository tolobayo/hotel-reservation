package model;

import java.util.Date;

public class Reservation {

    private Date checkInDate;
    private Date checkOutDate;
    private Customer customer;
    private IRoom room;
    
    public Reservation(Date checkInDate, Date checkOutDate, Customer customer, IRoom room) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.customer = customer;
        this.room = room;
    }

    public Date getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(Date checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Date getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(Date checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public IRoom getRoom() {
        return room;
    }

    public void setRoom(IRoom room) {
        this.room = room;
    }

    //TODO: Finsh method
    public boolean isDuplicate(Reservation res) {
        return res.customer.equals(this.customer);
    }

    @Override
    public String toString() {
        return "Reservation [checkInDate=" + checkInDate + ", checkOutDate=" + checkOutDate + ", customer=" + customer + ", room="
                + room + "]";
    }

    

}
