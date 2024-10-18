package cli;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Scanner;

import api.HotelResource;
import model.Customer;
import model.IRoom;
import model.Reservation;

public class MainMenu {
    private static Scanner scanner = new Scanner(System.in);
    public static void displayMainMenu() {
        boolean keepRunning = true;
        boolean enteredInvalidInput = false;

        while (keepRunning) {
            //if entered invalid input, dont show menu, only show prompt
            if(!enteredInvalidInput){// Display the menu options
            System.out.println("\nWelcome to the Hotel Reservation System");
            System.out.println("========================================");
            System.out.println("1. Find and reserve a room");
            System.out.println("2. See my reservations");
            System.out.println("3. Create an account");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.println("========================================");
            System.out.print("Please select a number for the menu option:\n");}
            enteredInvalidInput = false;

            // Get user input
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    findAndReserveRoom();
                    break;
                case "2":
                    viewReservations();
                    break;
                case "3":
                    createAccount();
                    break;
                case "4":
                    AdminMenu.displayAdminMenu();
                    keepRunning = false;
                    break;
                case "5":
                    System.out.println("\nThank you for using the Hotel Reservation System. Goodbye!");
                    keepRunning = false;
                    break;
                default:
                    System.out.println("\nPlease enter a number between 1 and 5.");
                    enteredInvalidInput = true;
                    break;
            }
        }
        scanner.close();
    }

    private static void findAndReserveRoom() {
        int caseNum = 1;
        boolean keepRunning = true;
        Date checkIn = null;
        Date checkOut = null;
        String email = null;

        while (keepRunning) {
            switch (caseNum) {
                case 1:
                    System.out.println("Enter CheckIn Date mm/dd/yyyy");
                    break;
                case 2:
                    System.out.println("Enter CheckOut Date mm/dd/yyyy");
                    break;
                case 3:
                    System.out.println("Would you like to book a room (Y/N)");
                    break;
                case 4:
                    System.out.println("Do you have an account with us?");
                    break;
                case 5:
                    System.out.println("Follow the steps below to create an account");
                    break;
                case 6:
                    System.out.println("Enter email format: name@domain.com");
                    break;
                case 7:
                    System.out.println("What room would you like to reserve");
                    break;
            }

            if (caseNum < 8) {
                String input = scanner.nextLine();

                //build needed values - get from user and switch to next prompt
                if (caseNum == 1) {
                    //validate date, if valid move on
                    LocalDate date = getDate(input);
                    
                    if (date != null) {
                        //convert local date to date
                        checkIn = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        caseNum++;
                        
                    }
                }
                else if (caseNum == 2) {
                    LocalDate date = getDate(input);
                    if (date != null) {
                        checkOut = Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
                        if (!checkOut.before(checkIn)) {
                            HotelResource hotelResource = HotelResource.getInstance();
                            Collection<IRoom> openRooms =  hotelResource.findARoom(checkIn, checkOut);
                            while (openRooms.size() == 0) {
                                System.out.println("No rooms available at this time");
                                //if no rooms available find next 7 rooms
                                Calendar checkInCalendar = Calendar.getInstance();
                                checkInCalendar.setTime(checkIn);
                                Calendar checkOutCalendar = Calendar.getInstance();
                                checkOutCalendar.setTime(checkOut);
                            
                                // Add 7 days to the current date
                                checkInCalendar.add(Calendar.DAY_OF_YEAR, 7);
                                checkOutCalendar.add(Calendar.DAY_OF_YEAR, 7);
                                checkIn = checkInCalendar.getTime();
                                checkOut = checkOutCalendar.getTime();
                                openRooms = hotelResource.findARoom(checkIn, checkOut);
                            
                                System.out.println("Searching for NEW dates " + checkInCalendar.getTime() + " to " + checkOutCalendar.getTime());
                            
                            }
                        
                            for (IRoom roomOpen : openRooms) System.out.println(roomOpen.toString());
                            caseNum++;
                        
                        }
                    }
                }
                else if (caseNum == 3) {
                    while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                        System.out.println("Please enter Y (Yes) or N (No)");
                        input = scanner.nextLine();
                    }
                    if (input.equalsIgnoreCase("y")) caseNum++;
                    else keepRunning = false;
                
                }
                else if (caseNum == 4) {
                    
                    while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n")) {
                        System.out.println("Please enter Y (Yes) or N (No)");
                        input = scanner.nextLine();
                    }
                    if (input.equalsIgnoreCase("y")) caseNum += 2;
                    else caseNum++;
                }
                else if (caseNum == 5) {
                    
                    //is always successful
                    createAccount();
                    caseNum++;

                }
                else if (caseNum == 6) {
                    //check for valid customer
                    HotelResource hotelResource = HotelResource.getInstance();
                    while (!(hotelResource.getCustomer(input) instanceof Customer)) {
                        System.out.println("Please enter a valid customer email");
                        input = scanner.nextLine();
                    }
                    caseNum++;
                    email = input;
                }
                else if (caseNum == 7) {
                    //check for valid customer
                    HotelResource hotelResource = HotelResource.getInstance();
                    IRoom desiredRoom = hotelResource.getRoom(input);
                    while (!(desiredRoom instanceof IRoom)) {
                        System.out.println("Please enter a valid room number");
                        input = scanner.nextLine();
                        desiredRoom = hotelResource.getRoom(input);
                    }
                    Reservation reserved = hotelResource.bookARoom(email, desiredRoom, checkIn, checkOut);
                    if(reserved == null) {
                        System.out.println("Cannot book invalid room, please try again");
                        caseNum = 1;
                    } else{
                        System.out.println(reserved.toString());
                        caseNum++;
                        keepRunning = false;
                    }
                }
        
            } 
        }
    }
    private static void viewReservations() {
        System.out.println("Please enter a valid customer email");
        String input = scanner.nextLine();
        //check for valid customer
        HotelResource hotelResource = HotelResource.getInstance();
        while (!(hotelResource.getCustomer(input) instanceof Customer)) {
            System.out.println("Please enter a valid customer email");
            input = scanner.nextLine();
        }

        for (Reservation reservation : hotelResource.getCustomersReservations(input)) {
            System.out.println(reservation.toString());
        }

    }
    private static void createAccount() {
        int caseNum = 1;
        boolean keepRunning = true;
        String email = null;
        String firstName = null;
        String lastName = null;

        while (keepRunning) {
            switch (caseNum) {
                case 1:
                    System.out.println("Enter your email");
                    break;
                case 2:
                    System.out.println("Enter your first name");
                    break;
                case 3:
                    System.out.println("Enter your last name");
                    break;
            }

            if (caseNum < 4) {
                String input = scanner.nextLine();

                //build needed values - get from user and switch to next prompt
                if (caseNum == 1) {
                    email = input;
                    caseNum++;
                }
                else if (caseNum == 2) {
                    firstName = input;
                    caseNum++;
                }
                else if (caseNum == 3) {
                    lastName = input;
                    caseNum++;
                }
        
            } else {
                try {
                    HotelResource hotelResource = HotelResource.getInstance();
                    hotelResource.createACustomer(email, firstName, lastName);
                    caseNum++;
                } catch (IllegalArgumentException e) {
                    System.out.println("Operation failed: " + e.getLocalizedMessage());
                    caseNum = 1;
                }
                if (caseNum > 4) keepRunning = false;
                
            }
        }   

    }
    public static LocalDate getDate(String dateStr) {
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        try {
            return LocalDate.parse(dateStr, formatter);
            
        } catch (DateTimeParseException e) {
            return null; 
        }
    }
    

}
