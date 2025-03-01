import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.util.InputMismatchException;




public class Main {
    static ArrayList<Admin> admins = new ArrayList<>();
    static ArrayList<Passenger> passengers = new ArrayList<>();
    static ArrayList<Flight>flights= new ArrayList<>();
    static ArrayList<Airport> airports= new ArrayList<>();

    public static void main(String[] args) throws IOException {
        loadFromFile("System_Data.json",flights,passengers,admins,airports);
        Flight.setFlightsNumbers();
        Scanner input = new Scanner(System.in);



        menu(input);


        writeToFile("System_Data.json",flights,passengers,admins,airports);
    }//main



    public static void menu(Scanner input) throws IOException {
        System.out.println("WELCOME TO THE FLIGHT BOOKING SYSTEM!");
        System.out.println("-------------------------------------");

        boolean exit = false;
        int choice;
        while (!exit) {
            System.out.println("-----------------------");
            System.out.println("1. Login");
            System.out.println("2. Create account");
            System.out.println("3. Exit");
            System.out.println("-----------------------");
            System.out.print("Enter your choice number: ");

            while (true) {
                try {
                    choice = input.nextInt();
                    input.nextLine();  // Clear the buffer
                    break;  // Exit the loop if input is valid
                } catch (InputMismatchException e) {
                    System.out.println("Invalid choice. Please enter a valid number.");
                    input.next(); // Clear the invalid input
                    System.out.print("Enter your choice number: ");
                }
            }

            switch (choice) {
                case 1: // Login
                    String userType;
                    String email;
                    String password;
                    while (true) {
                        System.out.println("Enter Email:");
                        email = input.next();
                        System.out.println("Enter Password:");
                        password = input.next();
                        userType = Passenger.login(email, password);
                        if (userType.equals("-1")) { // login failed
                            System.out.println("Invalid username or password! ");
                            System.out.println("1. Try again\n2. Exit");
                            try {
                                if (input.nextInt() == 2)
                                    break;
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Exiting login attempt.");
                                input.next(); // Clear the invalid input
                                break;
                            }
                        } else
                            break;
                    }

                    if (userType.equals("Admin")) {
                        Admin currentAdmin = null;
                        for (Admin admin : admins) {
                            if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
                                currentAdmin = admin;
                                break;
                            }
                        }

                        boolean continueAdminMenu = true;
                        while (continueAdminMenu) {
                            System.out.println("\n-------------------------------------\n");
                            Admin.displayAdminMenu();
                            try {
                                System.out.println("---------------------------------");
                                System.out.println("Enter your choice:");
                                choice = input.nextInt();
                                input.nextLine(); // Clear the buffer
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid choice. Please enter a valid number.");
                                input.next(); // Clear the invalid input
                                continue;
                            }

                            switch (choice) {
                                case 1:
                                    for (Flight flight : flights){

                                        flight.displayFlightDetails();


                                    }
                                    break;

                                case 2:
                                    Admin.addFlight(airports, flights);
                                    break;
                                case 3:
                                    Admin.updateFlightSchedule(flights);
                                    break;
                                case 4:
                                    Admin.adjustFlightDetails(flights);
                                    break;
                                case 5:
                                    Admin.deleteFlight(flights);
                                    break;
                                case 6:
                                    Admin.manageSeatAvailability(flights);
                                    break;
                                case 7:
                                    Admin.addAdmin(admins, input);
                                    break;
                                case 8:
                                    Admin.addAirport(airports);
                                    break;

                                case 9:
                                    Admin.displayAirports(airports);
                                    break;
                                case 10:
                                    Admin.deleteAirport(airports);
                                    break;
                                case 11:
                                    Admin.logout(currentAdmin);
                                    continueAdminMenu = false;
                                    break;

                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    Admin.displayAdminMenu();
                                    break;
                }
                        }
                    } else if (userType.equals("Passenger")) {
                        boolean continuePassengerMenu = true;
                        while (continuePassengerMenu) {
                            System.out.println("\n-------------------------------------\n");
                            Passenger.displayPassengerMenu();
                            try {
                                choice = input.nextInt();
                                input.nextLine(); // Clear the buffer
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid choice. Please enter a valid number.");
                                input.next(); // Clear the invalid input
                                continue;
                            }
                            Passenger currentPassenger = null;
                            for (Passenger passenger : passengers) {
                                if (passenger.getEmail().equals(email) && passenger.getPassword().equals(password)) {
                                    currentPassenger = passenger;
                                    break;
                                }
                            }
                            Booking booking = new Booking();

                            switch (choice) {
                                case 1:
                                    for (Flight flight : flights)
                                        flight.displayFlightDetails();
                                    break;
                                case 2:
                                    Booking booking1 = booking.bookTicket( flights, input);
                                    if (booking1 != null) {
                                        currentPassenger.addBooking(booking1);
                                        currentPassenger.getBookings().getLast().confirmBookingAfterPayment(booking);
                                    }
                                    else {
                                        System.out.println("The Booking has not been completed");
                                    }
                                    break;
                                case 3:
                                    booking.manageBooking(currentPassenger, input);
                                    break;
                                case 4:
                                    currentPassenger.displaybookings();
                                    break;
                                case 5:
                                    Passenger.logout(currentPassenger);
                                    continuePassengerMenu = false;
                                    break;
                                default:
                                    System.out.println("Invalid choice. Please try again.");
                                    Passenger.displayPassengerMenu();
                            }
                        }
                    }

                    break;

                case 2: // Sign Up
                    Passenger.creatAcount(passengers, admins);
                    break;

                case 3: // Exit
                    System.out.println("Exiting the program...");
                    exit = true;
                    break;

                default: // Invalid Choice
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        input.close();
    }




    public static void writeToFile(String filename, List<Flight> flights, List<Passenger> passengers,List<Admin> admins,List<Airport> airports) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonObject combinedData = new JsonObject();

        try (FileWriter writer = new FileWriter(filename)) {
            // Write both lists into JSON object under separate keys
            combinedData.add("flights", gson.toJsonTree(flights));
            combinedData.add("airports", gson.toJsonTree(airports));
            combinedData.add("passengers", gson.toJsonTree(passengers));
            combinedData.add("admins", gson.toJsonTree(admins));


            gson.toJson(combinedData, writer);
            System.out.println("Data successfully saved to " + filename);

        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }


    public static void loadFromFile(String filename, List<Flight> flights, List<Passenger> passengers,List<Admin> admins,List<Airport> airports) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileReader reader = new FileReader(filename)) {
            JsonObject combinedData = gson.fromJson(reader, JsonObject.class);

            Type flightListType = new TypeToken<ArrayList<Flight>>() {}.getType();
            List<Flight> loadedFlights = gson.fromJson(combinedData.get("flights"), flightListType);
            if (loadedFlights != null) {
                flights.addAll(loadedFlights);
            }

            Type airportListType = new TypeToken<ArrayList<Airport>>() {}.getType();
            List<Airport> loadedAirports = gson.fromJson(combinedData.get("airports"), airportListType);
            if (loadedAirports != null) {
                airports.addAll(loadedAirports);
            }

            Type admintListType = new TypeToken<ArrayList<Admin>>() {}.getType();
            List<Admin> loadedAdmins = gson.fromJson(combinedData.get("admins"), admintListType);
            if (loadedAdmins  != null) {
                admins.addAll(loadedAdmins );
            }

            Type passengerListType = new TypeToken<ArrayList<Passenger>>() {}.getType();
            List<Passenger> loadedPassengers = gson.fromJson(combinedData.get("passengers"), passengerListType);
            if (loadedPassengers != null) {
                passengers.addAll(loadedPassengers);
            }

            System.out.println("Data successfully loaded from " + filename);

        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
    }


}// class







//        Admin.loadAdmins();
//        Passenger.loadPassengers("passengers.json",passengers);
//        Flight.loadFlights("flights.json",flights);

//        Airport a1 = new Airport("cairo", "cairo", "IATA", 20);
//        Airport a2 = new Airport("New York", "New York", "IATA", 20);
//        Airport a3 = new Airport("osaka", "osaka", "IATA", 20);
//        Airport a4 = new Airport("kyoto", "kyoto", "IATA", 20);
//        Airport a5 = new Airport("dubai", "dubai", "IATA", 20);
//        Airport a6 = new Airport("tokyo", "tokyo", "IATA", 20);
//        Airport a7 = new Airport("paris", "paris", "IATA", 20);
//        Airport a8 = new Airport("ryad", "ryad", "IATA",  20);
//        Airport a9 = new Airport("hamad", "doha", "IATA",20);
//        Airport a10 = new Airport("aman", "aman", "IATA", 20);
//        Flight f1 = new Flight(a1, a2, "9:00AM", "5:00PM", 5000f, "egy", 40);
//        Flight f2 = new Flight(a3, a4, "5:00PM", "6:30PM", 1000f, "japan", 35);
//        Flight f3 = new Flight(a5, a6, "7:00AM", "12:00PM", 5000f, "japan", 40);
//        Flight f4 = new Flight(a7, a8, "5:00AM", "9:00PM", 4000f, "uk", 35);
//        Flight f5 = new Flight(a9, a10, "9:00AM", "2:00PM", 7000f, "qatar", 40);
//        Flight f6 = new Flight(a1, a4, "5:00PM", "9:00AM", 6500f, "uk", 25);
//        Flight f7 = new Flight(a1, a2, "7:00AM", "3:00PM", 4500f, "japan", 30);
//        Flight f8 = new Flight(a1, a2, "5:00AM", "2:00PM", 6000f, "uk", 55);
//        Flight f9 = new Flight(a1, a2, "1:00AM", "10:00AM", 4000f, "jordan", 30);
//        Flight f10 = new Flight(a9, a10, "5:00AM", "9:00AM", 6000f, "uk", 55);
//        flights.add(f1);
//        flights.add(f2);
//        flights.add(f3);
//        flights.add(f4);
//        flights.add(f5);
//        flights.add(f6);
//        flights.add(f7);
//        flights.add(f8);
//        flights.add(f9);
//        flights.add(f10);

////f1.display_available_seats_layout();
//        airports.add(a1);
//        airports.add(a2);
//        airports.add(a3);
//        airports.add(a4);
//        airports.add(a5);
//        airports.add(a6);
//        airports.add(a7);
//        airports.add(a8);
//        airports.add(a9);
//        airports.add(a10);