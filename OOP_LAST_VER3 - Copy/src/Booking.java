import java.util.Scanner;
import java.util.List;
import java.util.InputMismatchException;

public class Booking {
    private static int lastAssignedBookingId = 0;
    private int bookingId;
    private Flight flight;
    private String seatNumber;
    private String bookingStatus;
    private Ticket ticket;
    private Seat seat;
    private String seatLocation;
    private Payment payment;

    // constructor
    public Booking( Flight flight,String seatLocation,String bookingStatus, Ticket ticket , Payment payment) {
        this.bookingId = ++lastAssignedBookingId;
        this.flight = flight;
        this.seatLocation = seatLocation;
        this.ticket = ticket;
        this.bookingStatus=bookingStatus;
        this.payment = payment;
        this.seat = flight.selectSeat(seatLocation);
    }

    public  Booking(){

    }

    public Ticket getTicket() {
        return ticket;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }


    //function 6
    public void confirmBookingAfterPayment(Booking booking) {

        if ("completed".equals(booking.payment.getPaymentStatus())) {
            ticket.IsReserved(true);
            booking.setBookingStatus("confirmed");

            // Display confirmation details
            System.out.println("\n--- Booking Confirmation ---");
            System.out.println("Booking ID: " + booking.getBookingId());
            System.out.println("Seat Number: " + booking.seat.getNumber());
            System.out.println("Seat Location: " + booking.seat.getLocation());
            System.out.println("Booking Status: " + booking.getBookingStatus());
            System.out.println("\nFlight Details:");
            booking.flight.displayFlightDetails();
            booking.payment.displayPaymentDetails();
        } else if ("failed".equals(booking.payment.getPaymentStatus())) {
            System.out.println("Payment failed. Booking cannot be confirmed.");
        }
    }



    //function 7
    public void manageBooking(Passenger passenger ,Scanner scanner) {
        scanner = new Scanner(System.in);


        System.out.println("\nSelect a booking to modify:");
        List<Booking> passengerBookings = passenger.getBookings();

        if (passengerBookings.isEmpty()) {
            System.out.println("No bookings available. Returning to menu.");
            return;
        }

        // show all the bookings to the user
        for (int i = 0; i < passengerBookings.size(); i++) {
            Booking booking = passengerBookings.get(i);
            System.out.println((i + 1) + ". Booking ID: " + booking.getBookingId() +
                    ", Flight: " + booking.getFlight().getFlightNumber() +
                    ", Seat: " + booking.getSeatLocation());
        }

        //ask the user to select a booking
        int bookingChoice = 0;

        while (true) {
            try {
                System.out.print("Enter the booking number to modify (1, 2, ...): ");
                bookingChoice = scanner.nextInt();
                scanner.nextLine();

                if (bookingChoice >= 1 && bookingChoice <= passengerBookings.size()) {
                    break; // valid input
                } else {
                    System.out.println("Invalid choice. Please enter a valid booking number.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }

        Booking selectedBooking = passengerBookings.get(bookingChoice - 1);
        while (true) {

            System.out.println("\nWhat would you like to change in your booking?");
            System.out.println("1. Change Seat");
            System.out.println("2. Cancel Booking");
            System.out.println("3. Display Booking Details");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    changeSeat(selectedBooking, scanner);
                    return;
                case 2:
                    cancelBooking(selectedBooking ,passenger);
                    return;
                case 3:
                    displayBookingInfo(selectedBooking);
                    return;
                case 4:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    public void displayBookingInfo(Booking selectedBooking) {
        System.out.println("Booking Details:");
        System.out.println("Booking ID: " + selectedBooking.bookingId);
        System.out.println("Flight Number: " + selectedBooking.flight.getFlightNumber());
        System.out.println("Seat Number: " + selectedBooking.seat.getNumber());
        System.out.println("Seat Location: " + selectedBooking.seat.getLocation());
        System.out.println("Flying Date: " + selectedBooking.flight.getFlyingDate());
        System.out.println("Booking Status: " + selectedBooking.bookingStatus);
        System.out.println("----------------------------------------------------");
    }

    public Booking bookTicket( List<Flight> flights, Scanner scanner) {
        System.out.println("\n--- Start Ticket Booking ---");


        List<Flight> matchingFlights = Flight.SearchFlight(flights);


        Flight selectedFlight1 = Flight.SelectFlight(matchingFlights, scanner);
        Flight selectedFlight = null;
      if(selectedFlight1!=null){
          for(Flight flight : Main.flights){
              if(selectedFlight1.getFlightNumber()== flight.getFlightNumber()){

                  selectedFlight = flight;
              }

          }
      }

        if (selectedFlight == null) {
            System.out.println("No flight selected. Returning to menu.");
            return null;
        }

        Seat selectedSeat = null;
        while (true) {

            //Available Seats
            selectedFlight.display_available_seats_layout();

            System.out.print("\nEnter the seat location you want to book (A1,...): ");

            seatLocation = scanner.nextLine();

            selectedSeat = selectedFlight.selectSeat(seatLocation);

            if (selectedSeat != null && selectedSeat.isAvailable()) {
                break;
            }

            System.out.println("The selected seat is not available.");
            System.out.println("1. Choose another seat\n2. Cancel and return to menu");
            boolean validChoice = false;
            while (!validChoice) {
                System.out.print("Enter your choice (1 or 2): ");
                int choiceinput = scanner.nextInt();
                scanner.nextLine();
                if (choiceinput == 1) {
                    validChoice = true;
                } else if (choiceinput == 2) {
                    System.out.println("Booking process canceled. Returning to menu.");
                    return null;
                } else {
                    System.out.println("Invalid choice. Please enter 1 to retry or 2 to cancel.");
                }
            }
        }


        int choice = 0;

        while (true) {
            System.out.print("\nProceed to payment? (1 for yes, 2 for no): ");
            choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Proceeding to payment");
                break;
            } else if (choice == 2) {
                System.out.println("Booking process canceled. Returning to menu.");
                return null;
            } else {
                System.out.println("Invalid choice. Please enter 1 for yes or 2 for no.");
            }
        }
        seat = selectedSeat;
        flight = selectedFlight;
        ticket = new Ticket(flight.getFlyingDate(), seat);
        payment = new Payment(ticket);

        Booking booking = new Booking( flight, seat.getLocation(),"confirmed", ticket , payment);


        selectedFlight.updateSeatAvailability(seat.getLocation(), "Unavailable");
        seat.setBooked(true);

        System.out.println("\n--- Booking Details ---");
        System.out.println("Booking ID: " + lastAssignedBookingId);
        System.out.println("Flight Number: " + flight.getFlightNumber());
        System.out.println("Flying Date: " + flight.getFlyingDate());
        System.out.println("Seat Number: " + seat.getLocation());
        System.out.println("Seat Class: " + seat.getSeatClass());
        System.out.println("Base Price: $" + ticket.getFare());
        System.out.println("Taxes: $" + ticket.getTaxes());
        System.out.println("Fees: $" + ticket.getFees());
        System.out.println("Total Price: $" + (ticket.getFare() + ticket.getTaxes() + ticket.getFees()));
        System.out.println("Booking Status: " + booking.getBookingStatus());

        return booking;
    }


    private void changeSeat(Booking selectedBooking, Scanner scanner) {
        String locationToChange = selectedBooking.getSeatLocation();
        selectedBooking.flight.updateSeatAvailability(locationToChange, "available");
        selectedBooking.seat.setBooked(false);
        selectedBooking.ticket.setReserved(false);

        Seat newSeat = null;
        while (true) {
            System.out.println("\nAvailable Seats:");
            selectedBooking.flight.display_available_seats_layout();

            System.out.print("\nEnter the seat location you want to book (A1,...): ");
            String selectedSeatLocation = scanner.nextLine();

            newSeat = selectedBooking.flight.selectSeat(selectedSeatLocation);
            if (newSeat != null && newSeat.isAvailable()) {
                break; // Available seat selected
            }

            System.out.println("The selected seat is not available.");
            System.out.println("1. Choose another seat\n2. Cancel and return to menu");
            boolean validChoice = false;
            while (!validChoice) {
                System.out.print("Enter your choice (1 or 2): ");
                int choiceInput = scanner.nextInt();
                scanner.nextLine();
                if (choiceInput == 1) {
                    validChoice = true;
                } else if (choiceInput == 2) {
                    System.out.println("Editing process canceled. Returning to menu.");
                    return;
                } else {
                    System.out.println("Invalid choice. Please enter 1 to retry or 2 to cancel.");
                }
            }
        }

        double newPrice = newSeat.getPrice();
        double oldPrice = selectedBooking.seat.getPrice();

        Ticket newTicket = new Ticket(selectedBooking.flight.getFlyingDate(), newSeat);
        selectedBooking.ticket = newTicket;

        if (newPrice > oldPrice) {
            System.out.println("New seat is more expensive than the old seat.");
            System.out.println("the previous amount of " + oldPrice + " Will added to your account number " + selectedBooking.payment.getPaymentDetails() );

            Payment newPayment = new Payment(newTicket);
            selectedBooking.payment = newPayment;
            this.payment=newPayment;
            newPayment.setPaymentStatus("completed");
        } else if (oldPrice > newPrice) {
            System.out.println("The old seat is more expensive than the new seat.");
            System.out.println("A refund of " + (oldPrice - newPrice) +
                    " will be processed to your account " + selectedBooking.payment.getPaymentDetails());
        } else {
            System.out.println("The two seats are the same price. No additional charges.");
        }


        selectedBooking.setSeat(newSeat);
        selectedBooking.setSeatLocation(newSeat.getLocation());


        selectedBooking.flight.updateSeatAvailability(newSeat.getLocation(), "Unavailable");
        newSeat.setBooked(true);

        selectedBooking.confirmBookingAfterPayment(selectedBooking);
    }

    private void cancelBooking(Booking selectedBooking, Passenger passenger) {
        String locationToChange = selectedBooking.seatLocation;
        selectedBooking.flight.updateSeatAvailability(locationToChange, "available");
        selectedBooking.seat.setBooked(false);
        System.out.println("Booking has been successfully canceled. A refund of "
                + selectedBooking.seat.getPrice()
                + " will be processed to your account number "
                + selectedBooking.payment.getPaymentDetails()
                + " after the deduction of applicable taxes and fees.");

        selectedBooking.ticket.setReserved(false);

        passenger.getBookings().remove(selectedBooking);
    }


    public String getSeatLocation() {
        return seatLocation;
    }

    public void setSeatLocation(String seatLocation) {
        this.seatLocation = seatLocation;
        this.seat = flight.selectSeat(seatLocation);
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat =seat;
 }


}