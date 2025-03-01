import java.io.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Admin extends User  {
    private String employeeId;

    public Admin(int id, String name, String email, String phone, String employeeId, String password) {
        super(id, name, email, phone, password);
        this.employeeId = employeeId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }



    public static void addFlight(List<Airport> airports, List<Flight> flights) {
        Scanner scanner = new Scanner(System.in);
        String answer =" ";
        do {

            System.out.print("Enter departure airport name: ");
            String departureAirportName = scanner.nextLine();

            System.out.print("Enter departure airport location: ");
            String departureAirportLocation = scanner.nextLine();

            Airport departureAirport = null;
            try {
                for (Airport airport : airports) {

                    if (departureAirportName.equalsIgnoreCase(airport.getAirport_name()) &&
                            departureAirportLocation.equalsIgnoreCase(airport.getAirport_location())) {
                        departureAirport = airport;
                        break;
                    }
                }

                if (departureAirport == null) {
                    System.out.println("Departure airport not found. Please ensure the name and location are correct.");
                    return;
                }
            } catch (NullPointerException e) {
                System.out.println("the airport name or location has not been added properly. Please check.");
                return;
            }

            System.out.print("Enter arrival airport name: ");
            String arrivalAirportName = scanner.nextLine();

            System.out.print("Enter arrival airport location: ");
            String arrivalAirportLocation = scanner.nextLine();

            Airport arrivalAirport = null;
            try {
                for (Airport airport : airports) {
                    if (arrivalAirportName.equalsIgnoreCase(airport.getAirport_name()) &&
                            arrivalAirportLocation.equalsIgnoreCase(airport.getAirport_location())) {
                        arrivalAirport = airport;
                        break;
                    }
                }

                if (arrivalAirport == null) {
                    System.out.println("Arrival airport not found. Please ensure the name and location are correct.");
                    return;
                }
            } catch (NullPointerException e) {
                System.out.println("the airport name or location has not been added properly. Please check.");
                return;
            }

            try {
                System.out.print("Enter departure time (12:00 AM): ");
                String departureTime = scanner.nextLine();

                System.out.print("Enter arrival time (12:00 PM): ");
                String arrivalTime = scanner.nextLine();

                System.out.print("Enter arrival time (2024-12-10): ");
                String flyingdate = scanner.nextLine();

                double price = 0;
                boolean validPrice = false;
                while (!validPrice) {
                    try {
                        System.out.print("Enter flight price: ");
                        price = Double.parseDouble(scanner.nextLine());

                        if (price <= 0) {
                            System.out.println("Price must be a positive number. Please enter a valid price.");
                        } else {
                            validPrice = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a numeric positive value for price.");
                    }
                }

                int baggageAllowance = 0;
                boolean validBaggageAllowance = false;
                while (!validBaggageAllowance) {
                    try {
                        System.out.print("Enter baggage allowance (kg): ");
                        baggageAllowance = Integer.parseInt(scanner.nextLine());

                        if (baggageAllowance <= 0) {
                            System.out.println("Baggage allowance must be a positive number. Please enter a valid baggage allowance.");
                        } else {
                            validBaggageAllowance = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input! Please enter a numeric positive value for baggage allowance.");
                    }
                }

                System.out.print("Enter airline: ");
                String airline = scanner.nextLine();

                System.out.print("Enter services (comma-separated, or leave blank for none): ");
                String servicesInput = scanner.nextLine();
                List<String> services = new ArrayList<>();
                if (!servicesInput.isBlank()) {
                    String[] servicesArray = servicesInput.split(",");
                    for (String service : servicesArray) {
                        services.add(service.trim());
                    }
                }


                Flight adflight = new Flight(departureAirport, arrivalAirport, departureTime, arrivalTime, flyingdate, price, airline, baggageAllowance);
                adflight.setServices(services);
                adflight.setFlightNumber(Main.flights.size() + 1);
                flights.add(adflight);


                System.out.println("\n-----------------------------------\n");
                System.out.println("Flight created successfully!");
                System.out.println("\n-----------------------------------\n");


            } catch (Exception e) {
                System.out.println("An error occurred while creating the flight: " + e.getMessage());
            }

            System.out.print("Do you want to add another FLIGHT ? (yes or no)");
            answer = scanner.nextLine();
            System.out.println("-------------------------\n");

        }while(answer.equalsIgnoreCase("yes"));
    }



    //    Edit Flight's (deperture & arrival) time
    public static void updateFlightSchedule(List<Flight> flights) {
        Scanner input = new Scanner(System.in);
        String answer =" ";
        do {
            try {
                System.out.println("-----------------------------\n");
                System.out.print("Enter number of flight to update its schedule : ");
                int flightNumber = input.nextInt();
                input.nextLine();
                System.out.println("\n-----------------------------\n");
                boolean flightFound = false;
                for (Flight flight : flights) {
                    if (flight.getFlightNumber() == flightNumber) {
                        flightFound = true;



                        System.out.print("Enter new departure time (12:00 a.m.): ");
                        String newDepartureTime = input.nextLine();

                        System.out.print("Enter new arrival time (12:00 a.m.): ");
                        String newArrivalTime = input.nextLine();

                        System.out.print("Enter new flying date (2025-1-1): ");
                        String newFlyingDate = input.nextLine();

                        flight.setDepartureTime(newDepartureTime);
                        flight.setArrivalTime(newArrivalTime);
                        flight.setFlyingDate(newFlyingDate);
                        System.out.println("\n-------------------------------\n");
                        System.out.println("Flight schedule updated successfully!");
                        System.out.println("\n-------------------------------\n");
                        System.out.println("Flight Number: " + flightNumber);
                        System.out.println("New Departure Time: " + flight.getDepartureTime());
                        System.out.println("New Arrival Time: " + flight.getArrivalTime());
                        System.out.println("New Flying Date: " + flight.getFlyingDate());
                        break;

                    }


                }

                if (!flightFound) {
                    System.out.println("Flight not found...");
                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number for the flight.");
                input.nextLine();
            } catch (Exception e) {
                System.out.println("An error occurred while updating flight details: " + e.getMessage());
            }
            System.out.println("\n--------------------------");
            System.out.print("Do you want to update another flight's SCHEDULE ? (yes or no) ");
            answer = input.nextLine();
        }while (answer.equalsIgnoreCase("yes"));

    }


//    Editing Flight Detail that user choose

    public static void adjustFlightDetails(List<Flight> flights) {
        Scanner input = new Scanner(System.in);
        String answer = " ";

        try {
            System.out.println("\n-------------------------------------\n");
            System.out.print("Enter the flight number you want to Edit: ");
            int flightNumber = input.nextInt();
            input.nextLine();

            boolean flightFound = false;

            for (Flight flight : flights) {
                if (flight.getFlightNumber() == flightNumber) {
                    flightFound = true;
                    do {
                        System.out.println("\n--------------------");
                        System.out.println("Select the detail you want to adjust:");
                        System.out.println("1. Departure Time");
                        System.out.println("2. Arrival Time");
                        System.out.println("3. Flying Date");
                        System.out.println("4. Price");
                        System.out.println("5. Airline");
                        System.out.println("6. Baggage Allowance");
                        System.out.print("\nEnter your choice (1-5): ");
                        int choice = input.nextInt();
                        input.nextLine();
                        System.out.println("-------------------");
                        switch (choice) {
                            case 1:
                                System.out.print("Enter new departure time (12:00 a.m.): ");
                                String newDepartureTime = input.nextLine();
                                flight.setDepartureTime(newDepartureTime);
                                System.out.println("\nDeparture time updated successfully");
                                break;
                            case 2:
                                System.out.print("Enter new arrival time (12:00 p.m.): ");
                                String newArrivalTime = input.nextLine();
                                flight.setArrivalTime(newArrivalTime);
                                System.out.println("\nArrival time updated successfully.");
                                break;
                            case 3:
                                System.out.print("Enter new flying date (2024-12-10 ): ");
                                String flyingdate = input.nextLine();
                                flight.setFlyingDate(flyingdate);
                                System.out.println("\nFlying Date updated successfully.");
                                break;

                            case 4:
                                System.out.print("Enter new flight price: ");
                                double newPrice = input.nextDouble();
                                input.nextLine();  // Consume newline
                                flight.setPrice(newPrice);
                                System.out.println("\nFlight price updated successfully.");
                                break;
                            case 5:
                                System.out.print("Enter new airline: ");
                                String newAirline = input.nextLine();
                                flight.setAirline(newAirline);
                                System.out.println("\nAirline updated successfully.");
                                break;
                            case 6:
                                System.out.print("Enter new baggage allowance (in kg): ");
                                int newBaggageAllowance = input.nextInt();
                                flight.setBaggageAllowance(newBaggageAllowance);
                                System.out.println("\nBaggage allowance updated successfully.");
                                break;
                            default:
                                System.out.println("Invalid choice.  Exit......");
                                return;

                        }

                        System.out.println("-----------------------------");
                        System.out.println("Do you want to another detail ? (yes or no)");
                        answer = input.nextLine();
                    }while (answer.equalsIgnoreCase("yes"));

                    System.out.println("\nUpdated Flight Details :-\n");
                    flight.displayFlightDetails();
                    break;

                }

            }

            if (!flightFound) {
                System.out.println("Flight with the number " + flightNumber + " not found.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid number.");
            input.nextLine();
        } catch (Exception e) {
            System.out.println("An error occurred while adjusting flight details: " + e.getMessage());
        }
    }


    public static void manageSeatAvailability(List<Flight> flights) {
        Scanner scanner = new Scanner(System.in);
        String answer = " ";

        if (flights.isEmpty()) {
            System.out.println("No flights available to manage.");
            return;
        }

        System.out.println("\n---------------------------\n");
        System.out.print("Enter the flight number to manage its seats: ");
        int flightChoice;
        try {
            flightChoice = scanner.nextInt();
            scanner.nextLine();
        } catch (InputMismatchException e) {
            System.out.println("Invalid Input.  Exit....");
            scanner.nextLine();
            return;
        }

        Flight selectedFlight = null;
        for (Flight flight : flights) {
            if (flight.getFlightNumber() == flightChoice) {
                selectedFlight = flight;
                break;
            }
        }

        if (selectedFlight == null) {
            System.out.println("Invalid flight number. Please try again.");
            return;
        }

        List<Seat> flightSeats = selectedFlight.getSeats();

        while (true) {
            System.out.println("\n--- Seat Management ---");
            System.out.println("1. Display Available Seats");
            System.out.println("2. Modify Seat Availability");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number between 1 and 3.");
                scanner.nextLine(); //////////////
                continue;
            }

            switch (choice) {
                case 1: // Display Available Seats
                    int n = 0;
                    if (flightSeats == null || flightSeats.isEmpty()) {
                        System.out.println("No seats available.");
                    } else {
                        System.out.println("\n----------------------------------\n");
                        System.out.println("AVAILABLE SEATS\n");
                        for (Seat seat : flightSeats) {
                            if (seat.isAvailable()) {
                                System.out.print(seat.getLocation() + " ");
                            } else {
                                System.out.print("["+ seat.getLocation() + " : Not Available] ");

                            }
                            n++;
                            if (n%6==0){
                                System.out.println("\n");
                            }

                        }

                        System.out.println("\n----------------------------------\n");
                    }
                    break;

                case 2: // Modify Seat Availability
                    do{
                        System.out.println("\n-------------------------------\n");
                        System.out.print("Enter seat location to modify its availability (A1): ");
                        String seatToModify = scanner.nextLine();
                        boolean seatFound = false;

                        for (Seat seat : flightSeats) {
                            if (seat.getLocation().equalsIgnoreCase(seatToModify)) {
                                seatFound = true;

                                if (seat.isBooked()) {
                                    System.out.println("Sorry! Cannot modify a booked seat.");
                                    break;
                                }

                                System.out.println("1. Make seat not available");
                                System.out.println("2. Make seat available");
                                System.out.print("Enter your choice: ");
                                int modifyChoice;
                                try {
                                    modifyChoice = scanner.nextInt();
                                    scanner.nextLine();
                                } catch (InputMismatchException e) {
                                    System.out.println("Invalid input. Please enter 1 or 2.");
                                    scanner.nextLine();
                                    break;
                                }

                                if (modifyChoice == 1) {
                                    if (seat.isAvailable()) {
                                        seat.setAvailability("Not Available");
                                        System.out.println("Seat " + seat.getLocation() + " is now not available.");
                                    } else {
                                        System.out.println("Seat " + seat.getLocation() + " is already not available.");
                                    }
                                } else if (modifyChoice == 2) {
                                    if (!seat.isAvailable()) {
                                        seat.setAvailability("Available");
                                        System.out.println("Seat " + seat.getLocation() + " is now available.");
                                    } else {
                                        System.out.println("Seat " + seat.getLocation() + " is already available.");
                                    }
                                } else {
                                    System.out.println("Invalid choice. Please enter 1 or 2.");
                                }
                                break;
                            }

                        }

                        if (!seatFound) {
                            System.out.println("Seat " + seatToModify + " does not exist.");
                        }
                        System.out.println("\nDo you want to modify another seat's AVAILABILITY ?(yes or no)");
                        answer = scanner.nextLine();
                        System.out.println("-------------------------------");

                    }while (answer.equalsIgnoreCase("yes"));
                    break;

                case 3: // Exit
                    System.out.println("Exiting seat management.....");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
}
}


    // ADD NEW ADMIN
    public static void addAdmin(List<Admin> admins, Scanner input) {
        System.out.println("\n--- Add New Admin ---");

        while (true) {
            int id = 0;
            String name;
            String email = "";
            String employeeId = "";
            String phone = "";
            String password;

            boolean idUnique = false;
            boolean emailUnique = false;
            boolean empIdUnique = false;
            boolean phoneUnique = false;
            boolean exit = false;

            // Get a unique ID
            while (!idUnique) {
                try {
                    System.out.print("Enter Admin ID: ");
                    id = input.nextInt();
                    input.nextLine(); // Clear the buffer
                    idUnique = true;

                    for (Passenger passenger : Main.passengers) {
                        if (passenger.getId() == id) {
                            idUnique = false;
                            System.out.println("ID already exists in the system.");
                            break;
                        }
                    }

                    for (Admin admin : admins) {
                        if (admin.getId() == id) {
                            idUnique = false;
                            System.out.println("ID already exists in the system.");
                            break;
                        }
                    }

                    if (!idUnique) {
                        System.out.println("1.Exit 2.Try again");
                        if (input.nextInt() == 1) {
                            exit = true;
                            break;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    input.nextLine(); // Clear invalid input
                }
            }
            if (exit)
                break;

            // Get Admin Name
            System.out.print("Enter Admin Name: ");
            name = input.nextLine();

            // Get a unique Email
            while (!emailUnique) {
                try {
                    System.out.print("Enter Admin Email: ");
                    email = input.nextLine();
                    emailUnique = true;

                    for (Passenger passenger : Main.passengers) {
                        if (passenger.getEmail().equalsIgnoreCase(email)) {
                            emailUnique = false;
                            System.out.println("Email already exists in the system.");
                            break;
                        }
                    }

                    for (Admin admin : admins) {
                        if (admin.getEmail().equalsIgnoreCase(email)) {
                            emailUnique = false;
                            System.out.println("Email already exists in the system.");
                            break;
                        }
                    }

                    if (!emailUnique) {
                        System.out.println("1.Exit 2.Try again");
                        if (input.nextInt() == 1) {
                            exit = true;
                            break;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid option.");
                    input.nextLine(); // Clear invalid input
                }
            }
            if (exit)
                break;

            // Get a unique Employee ID
            while (!empIdUnique) {
                try {
                    System.out.print("Enter Admin Employee ID: ");
                    employeeId = input.nextLine();
                    empIdUnique = true;

                    for (Admin admin : admins) {
                        if (admin.getEmployeeId().equalsIgnoreCase(employeeId)) {
                            empIdUnique = false;
                            System.out.println("Employee ID already exists in the system.");
                            System.out.println("1.Exit 2.Try again");
                            if (input.nextInt() == 1) {
                                exit = true;
                                break;
                            }
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid option.");
                    input.nextLine(); // Clear invalid input
                }
            }
            if (exit)
                break;

            // Get a unique Phone Number
            while (!phoneUnique) {
                try {
                    System.out.print("Enter Your Phone Number (11 digits): ");
                    phone = input.nextLine().trim();

                    if (phone.length() == 11 && phone.matches("\\d+")) {
                        phoneUnique = true;
                    } else {
                        System.out.println("Phone number must be exactly 11 digits. Try again.");
                        continue;
                    }

                    for (Passenger passenger : Main.passengers) {
                        if (passenger.getPhone().equalsIgnoreCase(phone)) {
                            phoneUnique = false;
                            System.out.println("Phone number already exists in the system.");
                            break;
                        }
                    }

                    for (Admin admin : admins) {
                        if (admin.getPhone().equalsIgnoreCase(phone)) {
                            phoneUnique = false;
                            System.out.println("Phone number already exists in the system.");
                            break;
                        }
                    }

                    if (!phoneUnique) {
                        System.out.println("1.Exit 2.Try again");
                        if (input.nextInt() == 1) {
                            exit = true;
                            break;
                        }
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid option.");
                    input.nextLine(); // Clear invalid input
                }
            }
            if (exit)
                break;

            // Validate Password
            while (true) {
                System.out.print("Enter Your Password (at least 8 characters): ");
                password = input.nextLine().trim();

                if (password.length() >= 8) {
                    break;
                } else {
                    System.out.println("Password must be at least 8 characters. Try again.");
                }
            }

            // Create and add the new Admin
            Admin newAdmin = new Admin(id, name, email, phone, employeeId, password);
            admins.add(newAdmin);

            System.out.println("Admin added successfully!");
            break;
        }
    }

    // deletes an airport
    public static void deleteAirport(List<Airport>airports){
        Scanner scanner=new Scanner(System.in);
        if(airports.isEmpty()){
            System.out.println("No Airports Available to delete");
            return;
        }
        boolean validInput=false;
        while (!validInput){
            System.out.println("Enter Airport Code to Delete or Type 'cancel' to exit:");
            String input=scanner.nextLine();
            if(input.equalsIgnoreCase("cancel")){
                System.out.println("Operation cancelled");
                return;
            }
            boolean airportFound=false;
            for(Airport airport:airports){
                if(airport.getAirport_code().equalsIgnoreCase(input)){
                    airports.remove(airport);
                    System.out.println("Airport " + input + " has been deleted");
                    airportFound=true;
                    break;
                }
            }
            if(!airportFound){
                System.out.println("Airport " + input + " not found");
            }else {
                validInput=true;
         }
}
    }

    // ADD NEW Airport
    public static void addAirport(List<Airport>airports){
        Scanner scanner=new Scanner(System.in);
        System.out.println("Enter Airport Name: ");
        String name=scanner.nextLine();
        System.out.println("Enter Airport Location: ");
        String location=scanner.nextLine();
        System.out.println("Enter Airport Code: ");
        String code=scanner.nextLine();
        System.out.println("Enter Airport Capacity");
        int capacity;
        try{
            capacity=Integer.parseInt(scanner.nextLine());
            if(capacity<=0){
                System.out.println("Invalid capacity! Capacity must be greater than 0");
                return;
            }
        }catch (NumberFormatException e){
            System.out.println("Invalid input! Capacity must be a number");
            return;
        }
        for(Airport airport:airports){
            if(airport.getAirport_code().equalsIgnoreCase(code)){
                System.out.println("This airport code already exists. Please use a unique code");
                return;
            }
            if(airport.getAirport_name().equalsIgnoreCase(name)&&airport.getAirport_location().equalsIgnoreCase(location)){
                System.out.println("An airport with the same name and location already exists");
                return;
            }
        }
        airports.add(new Airport(name,location,code,capacity));

        System.out.println("Airport Added Successfully");
    }
    public static void displayAirports(List<Airport> airports) {
        if (airports.isEmpty()) {
            System.out.println("No Airports Available");
            return;
        }
        System.out.println("List of Airports:");
        for (Airport airport : airports) {
            System.out.println("Name: " + airport.getAirport_name() +
                    ", Location: " + airport.getAirport_location() +
                    ", Code: " + airport.getAirport_code() +
                    ", Capacity: " + airport.getAirport_capacity());
        }
    }


    public static void deleteFlight(List<Flight>flights){
        Scanner scanner=new Scanner(System.in);
        if(flights.isEmpty()){
            System.out.println("No Flight Available to delete");
            return;
        }
        boolean validInput=false;
        while(!validInput){
            System.out.println("Enter Flight Number to Delete or Type cancel to exist: ");
            String input=scanner.nextLine();
            if(input.equalsIgnoreCase("cancel")){
                System.out.println("Operations cancelled");
                return;
            }
            try {
                int inputFlightNumber=Integer.parseInt(input);
                boolean flightFound=false;
                for(Flight flight:flights){
                    if(flight.getFlightNumber()==inputFlightNumber){
                        flights.remove(flight);
                        System.out.println("Flight"+inputFlightNumber+"has been deleted");
                        flightFound=true;
                        break;
                    }
                }
                if(!flightFound){
                    System.out.println("Flight"+inputFlightNumber+"not found");
                }else{
                    validInput=true;
                }
            }catch (NumberFormatException e){
                System.out.println("Invalid input! Please enter a valid flight number or type 'cancel' to exit.");
            }
}
    }

    public static void displayAdminMenu() {
        System.out.println("Admin Menu:");
        System.out.println("1. View All Flights");
        System.out.println("2. Add Flight");
        System.out.println("3. Update Flight Schedule");
        System.out.println("4. Adjust Flight Details");
        System.out.println("5. Remove Flight");
        System.out.println("6. Manage Seat Availability");
        System.out.println("7. Add admin");
        System.out.println("8. Add Airport");
        System.out.println("9. view airports");
        System.out.println("10.delete airports");
        System.out.println("11.Logout");
}



    public void displayInfo() {
        System.out.println("Admin Info:\n" +
                "ID: " + getId() + '\n' +
                "Name: " + getName() + '\n' +
                "Email: " + getEmail() + '\n' +
                "PHONE: " + getPhone());
  }
    public static void logout(Admin current) {
        System.out.println(current.getName()+" has logged out.");
    }
}




//    // Write to file method
//    public static void writeToFile(String fileName, Admin admin) {
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Admin_data.txt", true))) {
//
//
//            String line = admin.getId() + "," + admin.getName() + "," + admin.getEmail() + "," +
//                    admin.getPhone() + "," + admin.getEmployeeId() + "," + admin.getPassword();
//
//
//            writer.write(line);
//            writer.newLine();
//
//
//            System.out.println("Admin successfully written to " + fileName);
//
//        } catch (IOException e) {
//
//            System.out.println("An error occurred while writing to the file:" + e.getMessage());
//
//        }
//    }



// Read Admin info from file and store it in array list
//    public static void loadAdmins() {
//        try (BufferedReader br = new BufferedReader(new FileReader("Admin_data.txt"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",", 6);
//                int id = Integer.parseInt(parts[0]);
//                Main.admins.add(new Admin(id, parts[1], parts[2], parts[3], parts[4], parts[5]));
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading admins file: " + e.getMessage());
//        }
//    }