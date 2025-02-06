package com.vehicle.App;

import java.awt.Point;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.vehicle.Parking.User;
import com.vehicle.Parking.Vehicle;
import com.vehicle.dao.*;

public class App {

    public static void main(String[] args) {
        SessionFactory factory = HibernateUtil.getSessionFactory();
        try (Session session = factory.openSession()) { 
            UserDao userDao = new UserDao(session);
            VehicleDao vehicleDao = new VehicleDao(session);
            ParkingSpotDao parkingSpotDao = new ParkingSpotDao(session);
            ParkingLotDao parkingLotDao = new ParkingLotDao(session);
            TicketDao ticketDao = new TicketDao(session);
            VehicleDisplay vd = new VehicleDisplay(session);
            Scanner scan = new Scanner(System.in);

            while (true) {
                System.out.println("1. Register for Your Vehicle parking spot\n2. Login to Your Credential\n3. Exit from page");
                int choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        registerUser(scan, userDao);
                        break;
                    case 2:
                        loginUser(scan, userDao, vehicleDao,vd );
                        break;
                    case 3:
                        System.exit(0);
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
        } finally {
            HibernateUtil.shutdown(); 
        }
    }

    private static void registerUser(Scanner scan, UserDao userDAO) {
        System.out.print("Enter username: ");
        String username = scan.next();
        System.out.print("Enter password: ");
        String password = scan.next(); 
        System.out.print("Enter email: ");
        String email = scan.next();
        System.out.print("Enter phone number: ");
        String phoneNumber = scan.next();

        User user = new User(username, password, email, phoneNumber);
        user.setUsername(username);
        user.setPassword(password); 
        user.setEmail(email);
        user.setPhone_number(phoneNumber);
        userDAO.saveUser(user); 
        System.out.println("User registered successfully.");
    }

    private static void loginUser(Scanner scan, UserDao userDAO, VehicleDao vehicleDao, 
            VehicleDisplay vd) {
            System.out.print("Enter username: ");
            String username = scan.next();

            System.out.print("Enter password: ");
            String passwordInput = scan.next();

            User user = userDAO.getUserByUsername(username);
            if (user != null && user.getPassword().equals(passwordInput)) {
            System.out.println("Login successful!");
            showCustomerDashboard(user, vehicleDao, vd);
              } 
            else {
            System.out.println("Invalid credentials.");
             }
}

    private static void showCustomerDashboard(User user, VehicleDao vehicleDao, VehicleDisplay vd) {
System.out.println("Customer Dashboard");
Scanner scan = new Scanner(System.in);

while (true) {
System.out.println("1. Enter new vehicle\n2. Exit a vehicle\n3. View parked vehicles\n4. View parking layout\n5. Logout");
int choice = scan.nextInt();

switch (choice) {
case 1:
handleVehicleEntry(scan, vehicleDao, vd);
break;
case 2:
handleVehicleExit(scan, vehicleDao, vd);
break;
case 3:
viewParkedVehicles(vehicleDao);
break;
case 4:
vd.display(); 
break;
case 5:
return; // Logout
default:
System.out.println("Invalid choice.");
}
}
}

    private static void handleVehicleEntry(Scanner scan, VehicleDao vehicleDao, VehicleDisplay vd) {
        System.out.println("Enter vehicle type ('#' for car, '$' for two-wheeler): ");
        char type = scan.next().charAt(0);
        
        
        if (type != '#' && type != '$') {
            System.out.println("Invalid vehicle type. Please enter '#' for car or '$' for two-wheeler.");
            return;
        }

       
        Point locate = vd.allocate(type);
        if (locate.x == -1) {
            System.out.println("Sorry, all parking spaces are full.");
            return;
        }
        System.out.print("Enter vehicle color: ");
        String color = scan.next();
       
        System.out.print("Enter vehicle number (e.g., GJ01-HN-4561): ");
        String vehNo = scan.next();
        
        System.out.print("Enter owner's name: ");
        scan.nextLine(); 
        String ownerName = scan.nextLine();

        System.out.print("Enter owner's phone number: ");
        String phoneNumber = scan.next();

       
        Vehicle vehicle = new Vehicle();
        vehicle.setType(type == '#' ? "Car" : "Bike");
        vehicle.setLicense_plate_no(vehNo);
        vehicle.setOwner_name(ownerName);
        vehicle.setColor(color);
        
        
       
        vehicleDao.saveVehicle(vehicle);

       
        logVehicleEntry(ownerName, vehNo, locate);
    }

    private static void handleVehicleExit(Scanner scan, VehicleDao vehicleDao, VehicleDisplay vd) {
    	vd.display();
        System.out.println("Enter the coordinates of the parking spot to exit (e.g., x y): ");
        
        int x = scan.nextInt();
        int y = scan.nextInt();
        
        Point locate = new Point();
        locate.x = x; 
        locate.y = y; 

        boolean removed = vd.remove(locate); 

        if (removed) {
            System.out.print("Enter vehicle number to exit: ");
            String exitingVehicleNo = scan.next();

            
            logVehicleExit(exitingVehicleNo);
            
           
            vehicleDao.removeVehicleByLicensePlate(exitingVehicleNo);
            
            System.out.println("Vehicle exited successfully.");
        } else {
            System.out.println("No vehicle found at that location.");
        }
    }

    private static void logVehicleEntry(String ownerName, String vehNo, Point locate) {
       try (PrintWriter pw = new PrintWriter(new FileWriter("log.txt", true))) {
           pw.println("Entry:");
           pw.println("Owner Name: " + ownerName);
           pw.println("Vehicle Number: " + vehNo);
           pw.println("Location of Parking (Coordinates): " + locate.x + ", " + locate.y);
           pw.println("Date & Time: " + LocalDateTime.now());
           pw.println();
       } catch (IOException e) {
           System.out.println("Error logging entry: " + e.getMessage());
       }
    }

    private static void logVehicleExit(String exitingVehicleNo) {
       try (PrintWriter pw = new PrintWriter(new FileWriter("log.txt", true))) {
           pw.println("Exit:");
           pw.println("Vehicle Number: " + exitingVehicleNo);
           pw.println("Date & Time: " + LocalDateTime.now());
           pw.println();
       } catch (IOException e) {
           System.out.println("Error logging exit: " + e.getMessage());
       }
    }

    private static void viewParkedVehicles(VehicleDao vehicleDao) {
       List<Vehicle> vehicles = vehicleDao.findAll(); 
       if (vehicles.isEmpty()) {
           System.out.println("No vehicles are currently parked.");
       } else {
           System.out.println("Currently parked vehicles:");
           for (Vehicle v : vehicles) {
               System.out.printf("Type: %s, License Plate: %s, Owner: %s\n", v.getType(), v.getLicense_plate_no(), v.getOwner_name());
           }
       }
    }
    }