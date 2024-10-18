package service;

import model.Customer;
import model.IRoom;
import model.Reservation;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Collection;

public class ReservationService {
    private static ReservationService reference = new ReservationService();

    private List<IRoom> roomList;
    private Set<Reservation> reservationList;

    private ReservationService() {
        roomList = new ArrayList<IRoom>();
        reservationList = new HashSet<Reservation>();
    }
    public static ReservationService getInstance() {
        return reference;
    }

    public void addRoom(IRoom room) {
        boolean duplicate = false;
        for (IRoom r : roomList) {
            if (r.getRoomNumber().equals(room.getRoomNumber())) {
               duplicate = true;
            }
        }

        if (!duplicate) roomList.add(room);

    }
    public IRoom getARoom(String roomId) {
        
        for (IRoom room : roomList) {
            if (room.getRoomNumber().equals(roomId)) {
               return room;
            }
        }
        return null;
        
    } 
    public Reservation reserveARoom(Customer customer, IRoom room, Date checkInDate, Date checkOutDate) {
        
        for (Reservation reserved : reservationList) {
            boolean sameRoomNumber = reserved.getRoom().getRoomNumber().equals(room.getRoomNumber());

            Date roomCheckIn = reserved.getCheckInDate();
            Date roomCheckOut = reserved.getCheckOutDate();

            boolean checkInOverlaps = (checkInDate.equals(roomCheckIn) || checkInDate.after(roomCheckIn)) && (checkInDate.equals(roomCheckIn) || checkInDate.before((roomCheckOut)));

            boolean checkOutOverlaps = (checkOutDate.equals(roomCheckOut) || checkOutDate.before(roomCheckOut)) && (checkOutDate.equals(roomCheckOut) || checkOutDate.after(roomCheckIn));


            if (sameRoomNumber && (checkInOverlaps || checkOutOverlaps)) {
                return null;
            }

        }
        Reservation newReservation = new Reservation(checkInDate, checkOutDate, customer, room);

        reservationList.add(newReservation);

        return newReservation;
    }

    public Collection<IRoom> findRooms(Date checkInDate, Date checkOutDate) {
        List<IRoom> roomsAvailble = new ArrayList<IRoom>();
        for (IRoom room : roomList) {
            boolean open = true;
            
            
            for (Reservation reservation : reservationList) {
              
                //if room has existing reservation, check dates
                if(reservation.getRoom().getRoomNumber().equals(room.getRoomNumber())) {
                    System.out.print("SAME ROOM NUMBER AS EXISTING RESERVATION" );
                    Date roomCheckIn = reservation.getCheckInDate();
                    Date roomCheckOut = reservation.getCheckOutDate();

                    //if desired start falls within reservation boundary skip  
                    if ((checkInDate.equals(roomCheckIn) || checkInDate.after(roomCheckIn)) && (checkInDate.equals(roomCheckIn) || checkInDate.before((roomCheckOut)))) {
                    
                        open = false;
                        break;
                    }
                    //if desired end falls within reservation boundary skip
                    if ((checkOutDate.equals(roomCheckOut) || checkOutDate.before(roomCheckOut)) && (checkOutDate.equals(roomCheckOut) || checkOutDate.after(roomCheckIn))) {
    
                        open = false;
                        break;
                    }

                }
            }
            if (open) roomsAvailble.add(room);

        }
        
        return roomsAvailble;
    }

    public Collection<Reservation> getCustomersReservation(Customer customer) {
        Collection<Reservation> reservations = new ArrayList<Reservation>();

        for (Reservation booking : reservationList ) {
            if (booking.getCustomer().equals(customer)) reservations.add(booking);
        }
        return reservations;
    }

    public void printAllReservation() {
        for (Reservation booking : reservationList) {
            System.out.println(booking.toString());
        }
    }
    public Collection<IRoom> getAllRooms() {
        return roomList;
    }
}
