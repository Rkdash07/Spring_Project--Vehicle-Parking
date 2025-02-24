package com.vehicle.App;

import java.awt.Point;
import java.io.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

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
            	System.out.println("\t\t\t~ ~ ~WELCOME TO PAKING SEVA PORTAL~ ~ ~");
                System.out.println("\n\t1. Register for Your Vehicle parking spot\n\t2. Login to Your Credential\n\t3. Exit from page");
                int choice = scan.nextInt();
                switch (choice) {
                    case 1:
                        registerUser(userDao);
                        break;
                    case 2:
                        loginUser(userDao, vehicleDao,vd );
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

    private static void registerUser(UserDao userDAO) {
        Scanner scan = new Scanner(System.in);

        String username = null;
        String password = null;
        String email = null;
        String phoneNumber = null;

        
        while (true) {
            System.out.print("Enter username \n Note:3-20 characters only: ");
            username = scan.next();
            if (username.matches("^[a-zA-Z0-9]{3,20}$")) {
                break;
            } else {
                System.out.println("Invalid username.");
            }
        }

   
        while (true) {
            System.out.print("Enter password \n Note:minimum of 8 as chracter:");
            password=scan.next();
            if (password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
                break;
            } else {
                System.out.println("Invalid password.");
            }
        }

       
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        while (true) {
            System.out.print("Enter email: ");
            email = scan.next();
            Matcher emailMatcher = emailPattern.matcher(email);
            if (emailMatcher.matches()) {
                break;
            } else {
                System.out.println("Invalid email");
            }
        }

        
        while (true) {
            System.out.print("Enter phone number: ");
            phoneNumber = scan.next();
            if (phoneNumber.matches("^[0-9]{10}$")) {
                break;
            } else {
                System.out.println("Invalid phone number");
            }
        }
        User user = new User();
        user.setUsername(username);
        user.setPassword(password); 
        user.setEmail(email);
        user.setPhone_number(phoneNumber);

        // Save the user
        userDAO.saveUser(user);
        System.out.println("User registered successfully.");
    }


    private static void loginUser( UserDao userDAO, VehicleDao vehicleDao, 
            VehicleDisplay vd) {
    	    Scanner scan = new Scanner(System.in);
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
System.out.println("\n\t1. Enter new vehicle\n\t2. Exit a vehicle\n\t3. View parked vehicles\n\t4. View parking layout\n\t5. Logout");
int choice = scan.nextInt();

switch (choice) {
case 1:
handleVehicleEntry(vehicleDao, vd);
break;
case 2:
handleVehicleExit(vehicleDao, vd);
break;
case 3:
viewParkedVehicles(vehicleDao);
break;
case 4:
vd.display(); 
break;
case 5:
return; 
default:
System.out.println("Invalid choice.");
}
}
}

    private static void handleVehicleEntry( VehicleDao vehicleDao, VehicleDisplay vd) {
        
    	Scanner scan = new Scanner(System.in);
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
        
        String vehNo;
        while (true) {
            System.out.print("Enter vehicle number (e.g., KA-18-2354): ");
            vehNo = scan.next();
            if (vehNo.matches("^[A-Z]{2}-\\d{2}-\\d{4}$")) {  
                break;
            } else {
                System.out.println("Invalid vehicle number format. Please use the format: KA-18-2354.");
            }
        }

       
        System.out.print("Enter owner's name: ");
        scan.nextLine();
        String ownerName = scan.nextLine();
        
        
        String phoneNumber;
        while (true) {
            System.out.print("Enter owner's phone number (10 digits): ");
            phoneNumber = scan.next();
            if (phoneNumber.matches("^[0-9]{10}$")) {
                break;
            } else {
                System.out.println("Invalid phone number. Please enter 10 digits (numbers only).");
            }
        }

      
        Vehicle vehicle = new Vehicle();
        vehicle.setType(type == '#' ? "Car" : "Bike");
        vehicle.setLicense_plate_no(vehNo);
        vehicle.setOwner_name(ownerName);
        vehicle.setColor(color);
        vehicleDao.saveVehicle(vehicle);
    
        System.out.println("Vehicle parked at location: [" + locate.x + ", " + locate.y + "]");
        logVehicleEntry(ownerName, vehNo, locate);
        System.out.print("Your vehicle parking spot available to use!!\n");
    }

    private static void handleVehicleExit(VehicleDao vehicleDao, VehicleDisplay vd) {
    	Scanner scan = new Scanner(System.in);
        vd.display();
        System.out.println("Enter the x coordinate of the parking spot to exit: ");
        int x = scan.nextInt();
        System.out.println("Enter the y coordinate of the parking spot to exit: ");
        int y = scan.nextInt();

        Point locate = new Point();
        locate.x = 2 * (x - 1) + 1;  
        locate.y = 2 * (y - 1) + 1;
        System.out.println("Removing the Vehicle from : [" + locate.x + ", " + locate.y + "]");
        boolean removed = vd.remove(locate);

        if (removed) {
            System.out.print("Enter vehicle number to exit: ");
            String license_plate_no = scan.next();
            vehicleDao.removeVehicleByLicensePlate(license_plate_no);

            System.out.println("Vehicle Exited successfully!!\n");
        } else {
            System.out.println("No vehicle found at that location.\n");
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
               System.out.printf("Type: %s, \tLicense Plate: %s,\tOwner: %s\n", v.getType(), v.getLicense_plate_no(), v.getOwner_name());
           }
       }
    }
    }