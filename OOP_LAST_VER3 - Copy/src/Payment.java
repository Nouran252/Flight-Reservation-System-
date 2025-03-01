import java.util.Scanner;

public class Payment {
    private static int lastAssignedPaymentId = 0;
    private String paymentId;            // Payment Id
    private double totalPaymentAmount;   // Total cost || payment Amount
    private String paymentMethod;        // Payment method
    private String paymentStatus;        // Payment status
    private String paymentDetails;       // Payment details
    private Ticket ticket;

    // Constructor
    public Payment(Ticket ticket) {
        this.paymentId = "P" + (++lastAssignedPaymentId);
        this.ticket = ticket; // Link the ticket
        this.paymentStatus = "Pending";
        calculateTotalAmount();
        processPricingAndPayment();
    }

    public void calculateTotalAmount() {

            this.totalPaymentAmount = ticket.getFare() + ticket.getTaxes() + ticket.getFees(); // Base fare + taxes + fees
    }

    public void processPricingAndPayment() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nTotal amount to be paid: $" + totalPaymentAmount);

        while (true) {
            System.out.println("Select your payment method:");
            System.out.println("1. Credit Card");
            System.out.println("2. Debit Card");
            System.out.println("3. Bank Transfer");
            System.out.println("4. Digital Wallet");
            System.out.println("5. Cancel");

            System.out.print("Enter your choice (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    this.paymentMethod = "Credit Card";
                    System.out.print("Enter your Credit Card number (16 digits): ");
                    this.paymentDetails = scanner.nextLine();
                    if (this.paymentDetails.length() != 16) {
                        System.out.println("Credit Card number must be 16 digits.");
                        continue;
                    }
                    completePayment();
                    return;
                case 2:
                    this.paymentMethod = "Debit Card";
                    System.out.print("Enter your Debit Card number (16 digits): ");
                    this.paymentDetails = scanner.nextLine();
                    if (this.paymentDetails.length() != 16) {
                        System.out.println("Debit Card number must be 16 digits.");
                        continue;
                    }
                    completePayment();
                    return;
                case 3:
                    this.paymentMethod = "Bank Transfer";
                    System.out.print("Enter your Bank Account number (14 digits): ");
                    this.paymentDetails = scanner.nextLine();
                    if (this.paymentDetails.length() != 14) {
                        System.out.println("Bank Account number must be 14 digits.");
                        continue;
                    }
                    completePayment();
                    return;
                case 4:
                    this.paymentMethod = "Digital Wallet";
                    System.out.print("Enter your Digital Wallet ID (12 characters): ");
                    this.paymentDetails = scanner.nextLine();
                    if (this.paymentDetails.length() != 12) {
                        System.out.println("Digital Wallet ID must be 12 characters.");
                        continue;
                    }
                    completePayment();
                    return;
                case 5:
                    this.paymentStatus = "Failed";
                    System.out.println("Payment process canceled. Exiting..");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void completePayment() {
        this.paymentStatus = "Completed";
        System.out.println("Payment completed successfully using " + paymentMethod + ". Total amount: $" + totalPaymentAmount);
        setTicketReservationStatus(true);
    }

    private void setTicketReservationStatus(boolean isReserved) {
        ticket.IsReserved(isReserved);
        ticket.setPaymentdate();
    }

    public void displayPaymentDetails() {
        System.out.println("Payment Details:");
        System.out.println("Payment ID: " + paymentId);
        System.out.println("Base Fare: $" + ticket.getFare());
        System.out.println("Taxes: $" + ticket.getTaxes());
        System.out.println("Fees: $" + ticket.getFees());
        System.out.println("Total Payment Amount: $" + totalPaymentAmount);
        System.out.println("Method: " + paymentMethod);
        System.out.println("Payment Details: " + paymentDetails);
        System.out.println("Status: " + paymentStatus);
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getPaymentDetails() {
        return paymentDetails;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public double getTotalPaymentAmount() {
        return totalPaymentAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentDetails(String paymentDetails) {
        this.paymentDetails = paymentDetails;
 }
}