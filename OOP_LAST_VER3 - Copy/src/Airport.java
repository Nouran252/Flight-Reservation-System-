
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Airport {
    private String airport_name;
    private String airport_location ;
    private String airport_code ; // the unique airport code (IATA or ICAO)
    private int airport_capacity ;

    //Constractor

    public Airport (String name , String location ){
        this.airport_name = name;
        this.airport_location =location;
    }
    public Airport (String name , String location , String code , int capacity){

        this.airport_name = name;
        this.airport_location =location;
        this.airport_code = code;
        this.airport_capacity = capacity;


    }
    // Methods
    //  setters and getters
    public void setAirport_name(String name){
        this.airport_name = name;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public void setAirport_location(String airport_location) {
        this.airport_location = airport_location;
    }

    public String getAirport_location() {
        return airport_location;
    }

    public void setAirport_code(String airport_code) {
        this.airport_code = airport_code;
    }

    public String getAirport_code() {
        return airport_code;
    }



    public void setAirport_capacity(int airport_capacity) {
        this.airport_capacity = airport_capacity;
    }

    public int getAirport_capacity() {
        return airport_capacity;
    }


    //Method to display airport details
    public void displayDetails(){
        System.out.println("Airport Name : "+airport_name);
        System.out.println("Airport Location : "+airport_location);
        System.out.println("Airport Code : "+airport_code);
        System.out.println("Capacity : "+airport_capacity +" Flights");
        //System.out.println("Number of flights : "+flights.size()+" Flights");
        System.out.println("Flights data: ");

    }


        @Override
    public String toString() {
        return "Airport Name: " + airport_name + "\n" +
                "Airport Location: " + airport_location + "\n" +
                "Airport Code: " + airport_code + "\n" +
                "Capacity: " + airport_capacity + " Flights\n" ;
               // "Number of Flights: " + flights.size() + "Flights";
  }



}





//// read function for airports
//    public static void loadairports() {
//        try (BufferedReader br = new BufferedReader(new FileReader("airports_data.txt"))) {
//            String line;
//            while ((line = br.readLine()) != null) {
//                String[] parts = line.split(",");
//                int capacity =Integer.parseInt(parts[3]);
//                Main.airports.add(new Airport(parts[0],parts[1],parts[2],capacity));
//            }
//        } catch (IOException e) {
//            System.out.println("Error reading airports file: " + e.getMessage());
//        }
//    }
//
//    // Write to file method
//    public static void writeToFile(String fileName, Airport airport) {
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
//
//
//            String line = airport.getAirport_name() + "," + airport.getAirport_location() + "," + airport.getAirport_code() + "," +
//                    airport.getAirport_capacity();
//
//            writer.write(line);
//            writer.newLine();
//
//
//        } catch (IOException e) {
//
//            System.out.println("An error occurred while writing to the file:" + e.getMessage());
//
//        }
//    }
