import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
    private static int lastAssignedNumber = 0;
    private int number;
    private Boolean IsReserved = false;  // Ticket status
    private String paymentdate;
    private String flyingdate;
    private Seat seat;                   // Seat details (getFare)
    private double taxes;                // Taxes (10% of base price)
    private double fees;                 // Fixed fees (100 units)

    // Constructor
    public Ticket(String flyingdate, Seat seat) {
        this.number = ++lastAssignedNumber;
        this.flyingdate = flyingdate;
        this.seat = seat;
        calculateTaxesAndFees(); // Initialize taxes and fees
    }

    public void setSeat(Seat seat) {     // Change seat
        this.seat = seat;
        calculateTaxesAndFees(); // Recalculate taxes and fees
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public double getFare() {
        return seat != null ? seat.getPrice() : 0.0;
    }

    public double getTaxes() {
        return taxes;
    }

    public double getFees() {
        return fees;
    }

    public Boolean getIsReserved() {
        return IsReserved;
    }

    public void IsReserved(Boolean IsReserved) {
        this.IsReserved = IsReserved;
        if (seat != null) {
            seat.setAvailability("unavailable");
        }
    }

    public void setReserved(Boolean reserved) {
        IsReserved = reserved;
    }

    private void calculateTaxesAndFees() {
        double baseFare = this.getFare();
        this.taxes = baseFare * 0.10; // Taxes = 10% of base price
        this.fees = 100.0; // Fixed fees = 100 units
    }

    public void setPaymentdate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.paymentdate = LocalDateTime.now().format(formatter);
    }

    public String getFlyingdate() {
        return flyingdate;
    }

    public void setFlyingdate(String flyingdate) {
        this.flyingdate = flyingdate;
    }

    public String getticketdetailes() {
        return ("Ticket Details: \n" +
                "Number: " + number + "\n" +
                "Base Price: $" + getFare() + "\n" +
                "Taxes: $" + taxes + "\n" +
                "Fees: $" + fees + "\n" +
                "Total Price: $" + (getFare() + taxes + fees) + "\n" +
                "Reservation Status: " + IsReserved + "\n" +
                "Payment Date: " + paymentdate + "\n" +
                "Flying Date: " + flyingdate + "\n" +
                "Seat Details: " + (seat != null ? seat.getLocation() + " (" + seat.getSeatClass() + ")" : "No Seat Assigned"));
}
}