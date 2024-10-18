package model;

import java.util.Objects;

public class Room implements IRoom {

    private String roomNumber;
    private Double price;
    private RoomType enumeration;

    

    public Room(String roomNumber, Double price, RoomType enumeration) {
        this.roomNumber = roomNumber;
        this.price = price;
        this.enumeration = enumeration;
    }

    @Override
    public String getRoomNumber() {
        return roomNumber;
    }

    @Override
    public Double getRoomPrice() {
        return price;
    }

    @Override
    public RoomType getRoomType() {
        return enumeration;
    }

    @Override
    public boolean isFree(Room room) {
        if (room instanceof FreeRoom) return true;
        else return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Room)) return false;
        Room room = (Room) o;
        return roomNumber.equals(room.roomNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomNumber);
    }


    @Override
    public String toString() {
        return "Room [roomNumber=" + roomNumber + ", price=" + price + ", enumeration=" + enumeration + "]";
    }

    

}
