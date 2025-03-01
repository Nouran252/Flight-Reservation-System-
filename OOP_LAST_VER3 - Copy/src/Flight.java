import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.*;
import java.io.*;


public class Flight {
    private  int flightNumber=0;
    private Airport departureAirport;
    private Airport arrivalAirport;
    private String flyingDate;
    private String departureTime;
    private String arrivalTime;
    private String airline;
    private List<Seat> seats; // Composition with Seats class
    private int totalRows = 10; // Total rows in seating arrangement
    private int seatsPerRow =6; // Seats per row
    private double price=50;
    private int baggageAllowance; // in kilograms
    private List<String>services; // e.g., Wi-Fi, meals


    // this constructor just for search
    public Flight(Airport departureAirport, Airport arrivalAirport){
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }
    // Constructor
    public Flight( Airport departureAirport, Airport arrivalAirport, String departureTime, String arrivalTime,String flyingDate ,
                   double price, String airline, int baggageAllowance) {
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.flyingDate=flyingDate;
        this.seats = new ArrayList<>();
        setSeats(seats);
        this.price = price;
        this.airline = airline;
        this.baggageAllowance = baggageAllowance;
        this.services = new ArrayList<>();
        //departureAirport.addFlight(this);// to add this flight to the list of flights in departure airport
        this.flightNumber=Main.flights.size();
    }

    // Getters and Setters
    public int getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(int flightNumber) {
        this.flightNumber = flightNumber;
    }

    public static void setFlightsNumbers() {
        for (int i = 0; i < Main.flights.size(); i++) {
            Main.flights.get(i).setFlightNumber(i + 1);
        }
    }

    public Airport getDepartureAirport() {
        return departureAirport;
    }

    public void setDepartureAirport(Airport departureAirport) {
        this.departureAirport = departureAirport;
    }

    public Airport getArrivalAirport() {
        return arrivalAirport;
    }

    public void setArrivalAirport(Airport arrivalAirport) {
        this.arrivalAirport = arrivalAirport;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(String arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void  changelistseats(List<Seat> seats){
        this.seats=seats;
    }

    private void setSeats(List<Seat> seats) {
        seats.clear(); // Clear the existing list to avoid conflicts
        int seatNumber =0;
        for (int row = 1; row <= totalRows; row++) { // Iterate rows first
            for (char col = 'A'; col < 'A' + seatsPerRow; col++) { // Iterate columns within the row
                String seatLocation = col + "" + row; // Seat location as "A1", "B1", "C1", etc.
                seatNumber++;
                if (row <= totalRows / 3) { // First class seats
                    seats.add(new Seat("First", seatLocation,seatNumber,price));
                } else if (row <= (2 * totalRows) / 3) { // Business class seats
                    seats.add(new Seat("Business", seatLocation,seatNumber,price));
                } else { // Economy class seats
                    seats.add(new Seat("Economy", seatLocation,seatNumber,price));
                }
            }
        }
    }


    // function to select a seat
    public Seat selectSeat(String seatLocation){
        Seat selectSeat=null;
        for(Seat seat: seats){
            if(seat.getLocation().equals(seatLocation)){
                selectSeat = seat;

            }
        }
        return selectSeat;
    }


    public void display_available_seats_layout() {
        System.out.println("Available Seats for this flight:");
        ArrayList<String> displayedClasses = new ArrayList<>(); // Track displayed seat classes
        int i = 0; // Counter for formatting seat locations

        for (Seat seat : seats) { // Iterate over all seats in the flight
            // Display seat class and price only once for each class
            if (!displayedClasses.contains(seat.getSeatClass())) {
                System.out.println("\n"+"Seats Class: " + seat.getSeatClass() + " | Seats Price: " + seat.getPrice()+"$");
                displayedClasses.add(seat.getSeatClass());
            }

            // Display the seat location if available
            if (seat.isAvailable()) {
                System.out.print(seat.getLocation() + " ");
                i++;
                if (i % seatsPerRow == 0) {
                    System.out.println(); // Newline after each row
                }
            }
        }
    }

    public void updateSeatAvailability(String seatLocation, String availabilityStatus) {
        for (Seat seat : seats) {
            if (seat.getLocation().equals(seatLocation)) {
                seat.setAvailability(availabilityStatus);
                //seat.setBooked(true);
                return;
            }
        }
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }


    public int getSeatsPerRow() {
        return seatsPerRow;
    }

    public void setSeatsPerRow(int seatsPerRow) {
        this.seatsPerRow = seatsPerRow;
    }

    public void addSeat(Seat s){
        seats.add(s);
    }

    // Method to calculate available seats
    public int getAvailableSeatsCount() {
        int count = 0;
        for (Seat seat : seats) {
            if (seat.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
        setSeats(seats); // Recalculate seat prices
    }

    public int getBaggageAllowance() {
        return baggageAllowance;
    }

    public void setBaggageAllowance(int baggageAllowance) {
        this.baggageAllowance = baggageAllowance;
    }

    public List<String> getServices() {
        return services;
    }

    public void setServices(List<String> services) {
        if(services.isEmpty())
            this.services= Collections.singletonList("None");
        this.services = services;
    }



    // passenger uses this function to search
    public static List<Flight> SearchFlight(List<Flight> flights)
    {
        Scanner input = new Scanner(System.in);
        List <Flight> matchingFlights = new ArrayList<>();
        System.out.println("\n--- Search Flights ---");
        System.out.println("Current Airports and locations: ");
        for (Airport airport: Main.airports){

            System.out.println("Name: "+ airport.getAirport_name());
            System.out.println("Location: "+airport.getAirport_location());
            System.out.println("---------------------------------");
        }
        System.out.println("Enter Departure Airport Name: ");
        String dAirport_name = input.nextLine();
        System.out.println("Enter Departure Airport Location: ");
        String dAirport_location = input.nextLine();
        Airport DA = new Airport(dAirport_name,dAirport_location);
        System.out.println("Enter Arrival Airport Name: ");
        String AAirport_name = input.nextLine();
        System.out.println("Enter Arrival Airport Location: ");
        String AAirport_location = input.nextLine();
        Airport AA = new Airport(AAirport_name,AAirport_location);
        Flight f = new Flight(DA,AA);
        // checks by the names and locations of the arrival and departure airports
        for(Flight flight : flights){
            if(flight.getDepartureAirport().getAirport_name().equalsIgnoreCase(f.getDepartureAirport().getAirport_name())&&
                    flight.getDepartureAirport().getAirport_location().equalsIgnoreCase(f.getDepartureAirport().getAirport_location())&&
                    flight.getArrivalAirport().getAirport_name().equalsIgnoreCase(f.getArrivalAirport().getAirport_name())&&
                    flight.getArrivalAirport().getAirport_location().equalsIgnoreCase(f.getArrivalAirport().getAirport_location())){
                matchingFlights.add(flight);
            }
        }
        return matchingFlights;
    }

    // passenger uses this function to select a flight
    // the results from the search method
    public static Flight SelectFlight(List<Flight> results, Scanner input) {
        Flight SelectedFlight = null;

        if (!results.isEmpty()) {
            int choice;
            while (true) {
                // Display available flights
                System.out.println("Available Flights:");
                System.out.println("\n--------------------------------------------------------------------------\n");

                for (int i = 0; i < results.size(); i++) {
                    System.out.println("Option " + (i + 1) + ":");
                    results.get(i).displayFlightDetails();
                    System.out.println("\n--------------------------------------------------------------------------\n");
                }

                while (true) {
                    System.out.println("Do you want to filter the flight based on the price?");
                    System.out.println("1. Yes\n2. No");
                    int ans = -1;

                    try {
                        ans = input.nextInt();
                        if (ans == 1) {
                            filterFlightBasedOnPrice(results);
                            System.out.println("\n--------------------------------------------------------------------------\n");
                            break;
                        } else if (ans == 2) {
                            break;
                        } else {
                            System.out.println("Invalid option. Please select 1 or 2.");
                        }
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a number.");
                        input.nextLine(); // clear the invalid input
                    }
                }

                System.out.println("Select a flight by entering the option number: ");
                try {
                    choice = input.nextInt();
                    input.nextLine(); // consume the newline

                    // Check passenger choice
                    if (choice > 0 && choice <= results.size()) {
                        break;
                    } else {
                        System.out.println("Invalid selection. Please choose a valid option.");
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a valid number.");
                    input.nextLine();
                }
            }

            // Set the selected flight
            SelectedFlight = results.get(choice - 1);
            System.out.println("You selected:");
            SelectedFlight.displayFlightDetails();

        } else {
            System.out.println("No flights available for selection.");
        }

        return SelectedFlight;
    }

    // Display flight details
    public void displayFlightDetails() {
        System.out.println("-------------------------------------");
        System.out.println("Flight Number: " + flightNumber);
        System.out.println("Departure Airport Name: " + departureAirport.getAirport_name());
        System.out.println("Departure Airport Location: " + departureAirport.getAirport_location());
        System.out.println("Arrival Airport Name: " + arrivalAirport.getAirport_name());
        System.out.println("Arrival Airport Location: " + arrivalAirport.getAirport_location());
        System.out.println("Departure Time: " + departureTime);
        System.out.println("Arrival Time: " + arrivalTime);
        System.out.println("Flying Date: " + flyingDate);
        System.out.println("Airline: " + airline);
        System.out.println("Available Seats: " + getAvailableSeatsCount());
        System.out.println("Price: " + price+" $");
        System.out.println("Baggage Allowance: " + baggageAllowance+" kg");
        System.out.print("Services : " );
        for (String serv : services)
        {
            System.out.print(serv+"    ");
        }
        System.out.print("\n");
        System.out.println("-------------------------------------");
    }


    public static void filterFlightBasedOnPrice(List<Flight>flights){
        if(flights.isEmpty()){
            System.out.println("No Flights available");
            return;
        }
        Scanner scanner=new Scanner(System.in);
        System.out.println("Do you want to view flights sorted by:");
        System.out.println("1- Highest Price");
        System.out.println("2- Lowest Price");
        System.out.print("Enter your choice (1 or 2): ");
        int choice;
        List<Flight> sortedFlights = new ArrayList<>(flights);
        try{
            choice=Integer.parseInt(scanner.nextLine());
        }catch (NumberFormatException e){
            System.out.println("Invalid input! Please enter 1 or 2");
            return;
        }
        if(choice==1){
            sortedFlights.sort(new Comparator<Flight>() {
                @Override
                public int compare(Flight f1,Flight f2){
                    return Double.compare(f2.getPrice(),f1.getPrice()); //sort by highest
                }
            });
            System.out.println("Flights sorted by highest price");
        }else if(choice==2){
            sortedFlights.sort(new Comparator<Flight>() {
                @Override
                public int compare(Flight f1, Flight f2) {
                    return Double.compare(f1.getPrice(),f2.getPrice()); //sort by lowest price
                }
            });
            System.out.println("Flights sorted by lowest price: ");
        }else{
            System.out.println("Invalid choice! Please enter 1 or 2");
            return;
        }
        //display sorted flights
        for (Flight flight : sortedFlights) {
            System.out.println("Flight Number: " + flight.getFlightNumber() +
                    ", Departure: " + flight.getDepartureAirport().getAirport_name() +
                    ", Arrival: " + flight.getArrivalAirport().getAirport_name() +
                    ", Price: $" + flight.getPrice());
        }
    }



    @Override
    public String toString() {
        return "Flight Number: " + flightNumber + "\n" +
                "Departure Airport: " + departureAirport.getAirport_name() + " (" + departureAirport.getAirport_location() + ")\n" +
                "Arrival Airport: " + arrivalAirport.getAirport_name() + " (" + arrivalAirport.getAirport_location() + ")\n" +
                "Departure Time: " + departureTime + "\n" +
                "Arrival Time: " + arrivalTime + "\n" +
                "Flying Date: "+ flyingDate + "\n" +
                "Airline: " + airline + "\n" +
                "Available Seats: " + getAvailableSeatsCount() + "\n" +
                "Price: " + price + " $\n" +
                "Baggage Allowance: " + baggageAllowance + " kg\n" +
                "Services: " + String.join(", ", services);
    }


    public String getFlyingDate() {
        return flyingDate;
    }

    public void setFlyingDate(String flyingDate) {
        this.flyingDate = flyingDate;
    }
}




//    public static void writeToFlightsFile(String filename,List<Flight>flights){
//        Gson gson=new GsonBuilder().setPrettyPrinting().create();
//        try(FileWriter writer=new FileWriter(filename,false)){
//            System.out.println("write to file"+filename);
//            System.out.println("number of flights"+flights.size());
//            gson.toJson(flights,writer);
//            System.out.println("writing complete");
//        }catch (IOException e){
//            System.out.println("error while writing to file "+e.getMessage());
//        }
//    }
//
//    public static void loadFlights(String filename,ArrayList<Flight>flights){
//        Gson gson=new GsonBuilder().setPrettyPrinting().create();
//        try (FileReader reader=new FileReader(filename)){
//            Type flightsListType = new TypeToken<ArrayList<Flight>>() {}.getType();
//            ArrayList<Flight>loadedFlights=gson.fromJson(reader,flightsListType);
//            if(loadedFlights!=null){
//                flights.addAll(loadedFlights);
//                System.out.println("Flights successfully from JSON file");
//            }else{
//                System.out.println("No flights found in the file");
//            }
//
//        }catch (IOException e){
//            System.out.println("Error reading from file"+e.getMessage());
//        }
//    }