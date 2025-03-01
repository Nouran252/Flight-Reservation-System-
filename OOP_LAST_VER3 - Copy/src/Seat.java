public class Seat {
    private int number ;
    private String seatClass;
    private String location ;
    //private Boolean Isavaliable = true;
    private double basePrice;  // Base price from the Flight class
    private double price;      // Final price after adjustment
   private String availability="available" ;
   private Payment payment;
    private boolean isBooked=false; //to check if there is a passenger booked this seat
//    public Seat( String seatClass, String location,int number) {
//        this.number = number;
//        this.seatClass = seatClass;
//        this.location = location;
//
//        // Generate the code automatically during initialization
//        //this.code = generateCode();
//    }

    // Constructor
    public Seat(String seatClass, String location,int number ,double basePrice) {
        // Validate seat class
        if (!seatClass.equalsIgnoreCase("economy") &&
                !seatClass.equalsIgnoreCase("business") &&
                !seatClass.equalsIgnoreCase("first")) {
            throw new IllegalArgumentException("Invalid seat class: " + seatClass);
        }
        this.number = number;
        this.seatClass = seatClass;
        this.location = location;
        this.basePrice = basePrice;
        setPrice(); // Automatically calculate the price based on the seat class
        this.payment = payment;
    }

//    public Boolean getIsavaliable() {
//        return Isavaliable;
//    }
//    public void setAvailability(Boolean Isavaliable) {
//        this.Isavaliable = Isavaliable;
//    }
    public double getPrice() {
        return price;
    }

    // Calculate the price based on the seat class
    private void setPrice() {
        switch (seatClass.toLowerCase()) {
            case "economy":
                this.price = basePrice; // Base price for Economy
                break;
            case "business":
                this.price = basePrice * 1.5; // 1.5x for Business
                break;
            case "first":
                this.price = basePrice * 2; // 2x for First
                break;
            default:
                throw new IllegalArgumentException("Invalid seat class: " + seatClass);
        }
    }

    //public boolean isAvailable() {return this.Isavaliable ;}

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getSeatClass(){
        return seatClass ;
    }

    public void setSeatClass(String seatClass) {
        this.seatClass = seatClass;
    }

//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

//    //number : 20 , seatClass : second , location : A1 >>>> code : E20W
//    public String generateCode() {
//        if (seatClass == null || location == null) {
//            throw new IllegalArgumentException("Number, seatClass, and location must be valid.");
//        }
//        String classCode = seatClass.substring(0, 1).toUpperCase();
//        String locationCode = location.substring(0, 2).toUpperCase();
//        code = classCode + locationCode ;
//        return code ;
//    }

    public boolean isAvailable() {
        if (availability != null && availability.toLowerCase().equals("available")) {
            return true;
        }
        return false;
    }
    // Getter for booked
    public boolean isBooked() {
        return isBooked;
    }

    // Setter for booked
    public void setBooked(boolean booked) {
        this.isBooked=booked;
 }



    public void displaySeatdetails() {
        System.out.println("The Seat Numper :"+number);
        System.out.println("Seat Details:");
        System.out.println("Class: " + seatClass);
        System.out.println("Location: " + location);
        System.out.println("Availability: " + availability);
        System.out.println("Price: " + price + " $");
    }

    @Override
    public String toString() {
        return "Seat{" +
                "number=" + number +
                ", seatClass='" + seatClass + '\'' +
                ", location='" + location + '\'' +
                ", basePrice=" + basePrice +
                ", price=" + price +
                ", availability='" + availability + '\'' +
                ", isBooked=" + isBooked +
                '}';
    }
}