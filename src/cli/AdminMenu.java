package cli;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

import model.Customer;
import model.IRoom;
import model.Room;
import model.RoomType;
import api.AdminResource;

public class AdminMenu {
    private static Scanner scanner = new Scanner(System.in);
    public static void displayAdminMenu() {
        
        boolean keepRunning = true;
        boolean validInput = true;

        while (keepRunning) {

            if(validInput) {
                // Display the menu options
                System.out.println("\nAdmin Menu");
                System.out.println("========================================");
                System.out.println("1. See all Customers");
                System.out.println("2. See all Rooms");
                System.out.println("3. See all Reservations");
                System.out.println("4. Add a Room");
                System.out.println("5. Back to Main Menu");
                System.out.println("========================================");
                System.out.print("Please select a number for the menu option:\n");
            }
            //reset input tracker
            validInput = true;

            // Get user input
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    seeAllCustomers();
                    break;
                case "2":
                    seeAllRooms();
                    break;
                case "3":
                    seeAllReservations();
                    break;
                case "4":
                    addRoom();
                    break;
                case "5":
                    MainMenu.displayMainMenu();
                    keepRunning = false;
                    break;
                default:
                    System.out.println("Please enter a number between 1 and 5:\n");
                    validInput = false;
                    break;
            }
        }
        scanner.close();
    }
    private static void seeAllCustomers() {
        AdminResource adminResource = AdminResource.getInstance();
        Collection<Customer> customers = adminResource.getAllCustomers();
        for (Customer person : customers) {
            System.out.println(person.toString());
        }

    }
    private static void seeAllRooms() {
        AdminResource adminResource = AdminResource.getInstance();
        Collection<IRoom> rooms = adminResource.getAllRooms();
        for (IRoom room : rooms) {
            System.out.println(room.toString());
        }

    }
    private static void seeAllReservations() {
        AdminResource adminResource = AdminResource.getInstance();
        adminResource.displayAllReservations();
    }
    private static void addRoom() {
        int caseNum = 1;
        boolean keepRunning = true;
        String roomNumber = null;
        double price = 0;
        RoomType roomType = null;

        while (keepRunning) {
            switch (caseNum) {
                case 1:
                    System.out.println("Enter room number");
                    break;
                case 2:
                    System.out.println("Enter price per night");
                    break;
                case 3:
                    System.out.println("Enter room type: 1 for single bed 2 for double bed");
                    break;
                case 4:
                    System.out.println("Would you like to add another room (Y/N)");
                    break;
            }

            if (caseNum < 5) {
                String input = scanner.nextLine();
                //valid date input
                input = validateRoomOptions(caseNum, input);

                //build needed values - get from user and switch to next prompt
                if (caseNum == 1) {
                    roomNumber = input;
                    caseNum++;
                }
                else if (caseNum == 2) {
                    price = Double.parseDouble(input);
                    caseNum++;
                }
                else if (caseNum == 3) {
                    if (input.equals("1")) roomType = RoomType.SINGLE;
                    else if (input.equals("2")) roomType = RoomType.DOUBLE;
                    caseNum++;
                }
                else if (caseNum == 4) {
                    List<IRoom> rooms = new ArrayList<IRoom>();
                    IRoom room = new Room(roomNumber, price, roomType);
                    rooms.add(room);
                    AdminResource adminResource = AdminResource.getInstance();
                    adminResource.addRoom(rooms);
                    if (input.equalsIgnoreCase("y")) caseNum = 1;
                    else caseNum++;
                }
            } else keepRunning = false;
            
        }


    }
    private static String validateRoomOptions(int caseNum, String input) {
        switch (caseNum) {
            case 1:
                while (!isInteger(input)) {
                    System.out.println("Please enter an integer");
                    input = scanner.nextLine();
                }
                break;
            case 2:
                while (!isDouble(input)) {
                    System.out.println("Please enter a price");
                    input = scanner.nextLine();
                }
                break;

            case 3:
                while (!isInteger(input) || (Integer.parseInt(input) != 1 && Integer.parseInt(input) != 2)) {
                    System.out.println("Please enter 1 or 2");
                    input = scanner.nextLine();
                }
                break;

            case 4:
                while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                    System.out.println("Please enter Y (Yes) or N (No)");
                    input = scanner.nextLine();
                }
                break;
        }
        return input;
    }
    private static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);  
            return true;  
        } catch (NumberFormatException e) {
            return false;  
        }
    }
    private static boolean isDouble(String str) {
        try {
            Double.parseDouble(str);  
            return true;  
        } catch (NumberFormatException e) {
            return false;  
        }
    }


}
