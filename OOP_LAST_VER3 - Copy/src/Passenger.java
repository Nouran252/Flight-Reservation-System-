
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Passenger extends User {

    private String gender;
    private String nationality;
    private String passportNumber;
    private List<Booking> bookings;

    public Passenger(int id, String name, String gender, String nationality, String passportNumber, String email, String phone, String password) {
        super(id, name, email, phone, password);
        this.gender = gender;
        this.nationality = nationality;
        this.passportNumber = passportNumber;
        this.bookings = new ArrayList<>();
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public void setBookings(List<Booking> bookings) {
        this.bookings = bookings;
    }

    public void addBooking(Booking booking) {
        if (booking == null) {
            System.out.println("Error: Booking is null. Cannot add to bookings.");
            return;
        }
        if (bookings == null) {
            System.out.println("Warning: Bookings list not initialized. Initializing...");
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        System.out.println("Booking added successfully. Total bookings for " + getName() + ": " + bookings.size());
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
            this.gender = gender;
        } else {
            throw new IllegalArgumentException("Gender must be 'Male' or 'Female'.");
        }
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        if (nationality != null && !nationality.isEmpty()) {
            this.nationality = nationality;
        } else {
            throw new IllegalArgumentException("Please enter your nationality .");
        }
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        if (passportNumber != null && !passportNumber.isEmpty()) {
            this.passportNumber = passportNumber;
        } else
            throw new IllegalArgumentException("Please enter your passport number .");
    }


    @Override
    //overriding function from user that displays passenger info
    public void displayInfo() {
        System.out.println("Passenger :\n" +
                "ID: " + getId() + '\n' +
                "NAME: " + getName() + '\n' +
                "GENDER: " + gender + '\n' +
                "NATIONALITY: " + nationality + '\n' +
                "PASSPORT NUMBER: " + passportNumber + '\n' +
                "EMAIL ADDRESS: " + getEmail() + '\n' +
                "PHONE: " + getPhone() + '\n');
    }

    public void displaybookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found for this passenger.");
        } else {
            System.out.println("Your current Bookings:");
            for (Booking booking : bookings) {
                booking.displayBookingInfo(booking);
            }
        }
    }


    public static void displayPassengerMenu() {
        System.out.println("Passenger Menu:");
        System.out.println("1. View Available Flights");
        System.out.println("2. Book Flight");
        System.out.println("3. Manege Booking");
        System.out.println("4. View bookings");
        System.out.println("5. Logout");
    }


    // sign up method
    public static void creatAcount(List<Passenger> passengers, List<Admin> admins) throws IOException {
        Scanner scanner = new Scanner(System.in);

        // Enter Username
        String username;
        while (true) {
            System.out.print("Enter Username: ");
            username = scanner.nextLine().trim();

            boolean usernameExists = false;
            for (Passenger passenger : passengers) {
                if (passenger.getName().equals(username)) {
                    usernameExists = true;
                    break;
                }
            }

            if (usernameExists) {
                System.out.println("Username already exists. Please enter a different username");
            } else {
                break;
            }
        }

        System.out.print("Enter Your Gender: ");
        String gender = scanner.nextLine().trim();

        System.out.print("Enter Your Nationality: ");
        String nationality = scanner.nextLine().trim();

        System.out.print("Enter Your PassportNumber: ");
        String passportNumber = scanner.nextLine().trim();

        // Validate email
        String email;
        while (true) {
            System.out.print("Enter Your Email: ");
            email = scanner.nextLine().trim();

            boolean emailExists = false;

            // Check if email exists in passengers
            for (Passenger passenger : passengers) {
                if (passenger.getEmail().equalsIgnoreCase(email)) {
                    emailExists = true;
                    break;
                }
            }

            // Check if email exists in admins
            for (Admin admin : admins) {
                if (admin.getEmail().equalsIgnoreCase(email)) {
                    emailExists = true;
                    break;
                }
            }

            if (emailExists) {
                System.out.println("Email already exists. Please enter a different email.");
            } else {
                break;
            }
        }

        // Validate phone number
        String phone;
        while (true) {
            System.out.print("Enter Your Phone Number (11 digits): ");
            phone = scanner.nextLine().trim();

            if (phone.length() == 11 && phone.matches("\\d+")) {
                break;
            } else {
                System.out.println("Phone number must be exactly 11 digits. Try again");
            }
        }

        // Validate password
        String password;
        while (true) {
            System.out.print("Enter Your Password (8 characters): ");
            password = scanner.nextLine().trim();

            if (password.length() >= 8) {
                break;
            } else {
                System.out.println("Password must be exactly 8 characters. Try again");
            }
        }

        // Create a new passenger
        Passenger newPassenger = new Passenger(passengers.size() + 1, username, gender, nationality, passportNumber, email, phone, password);

        // Add passenger to the list
        passengers.add(newPassenger);

        System.out.println("--------------------------------------------------------");
        System.out.println("You successfully create your account!");
        System.out.println("Your Id is: " + (passengers.size()));

    }


    public  static void logout(Passenger current) {
        System.out.println(current.getName()+" has logged out.");
    }
}




//    public static void writeToFile(String fileName, List<Passenger> passengers) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        try (FileWriter writer = new FileWriter(fileName, false)) {  // false = overwrite the file
//            // Debugging: Check the number of passengers
//            System.out.println("Writing to file: " + fileName);
//            System.out.println("Number of passengers to write: " + passengers.size());
//
//            // Write the entire list of passengers to the file
//            gson.toJson(passengers, writer);
//            System.out.println("Writing complete!");
//        } catch (IOException e) {
//            System.out.println("Error writing to file: " + e.getMessage());
//        }
//    }


//    public static void loadPassengers(String fileName, ArrayList<Passenger> passengers) {
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//        try (FileReader reader = new FileReader(fileName)) {
//            // Define the type for ArrayList<Passenger>
//            Type passengerListType = new TypeToken<ArrayList<Passenger>>() {}.getType();
//
//            // JSON file into the passed ArrayList<Passenger>
//            ArrayList<Passenger> loadedPassengers = gson.fromJson(reader, passengerListType);
//
//            if (loadedPassengers != null) {
//                passengers.addAll(loadedPassengers);
//                System.out.println("Passengers and their bookings loaded successfully from JSON file.");
//            } else {
//                System.out.println("No passengers found in the file.");
//            }
//
//        } catch (IOException e) {
//            System.out.println("Error reading from file: " + e.getMessage());
//        }
//    }