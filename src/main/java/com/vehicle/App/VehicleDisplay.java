package com.vehicle.App;

import java.awt.Point;

import org.hibernate.Session;

interface intfmat {
    Point allocate(char type);
    boolean remove(Point locate);
    void display();
    char mat[][] = new char[32][32]; // 32x32 grid
}

class VehicleDisplay implements intfmat {
    public char mat[][];

    // Default constructor to initialize the grid
    VehicleDisplay() {
        mat = new char[32][32];
        initializeGrid(); // Initialize the grid with empty spaces
    }

    // Constructor with Session to initialize the grid (if needed)
    public VehicleDisplay(Session session) {
        mat = new char[32][32];
        initializeGrid(); // Initialize the grid with empty spaces
    }

    // Method to initialize the grid with empty spaces
    private void initializeGrid() {
        // Initialize all cells to empty spaces
        for (int j = 0; j < 32; j++) {
            for (int k = 0; k < 32; k++) {
                mat[j][k] = ' ';
            }
        }

        // Add borders and parking lines
        for (int j = 0; j < 32; j++) {
            for (int k = 0; k < 32; k++) {
                // Top and bottom borders (excluding last row)
                if (j == 0 || (j == 31 && k != 31 && k != 30 && k != 29)) {
                    mat[j][k] = '-';
                }
                // Left and right borders
                if (k == 0 || k == 31) {
                    mat[j][k] = '|';
                }
            }
        }

        // Horizontal lines for parking rows
        for (int j = 1; j < 31; j += 2) {
            for (int k = 1; k < 29; k++) {
                mat[j][k] = '-';
            }
        }

        // Vertical lines for parking columns
        for (int j = 2; j < 31; j += 4) {
            for (int k = 2; k < 29; k += 2) {
                mat[j][k] = '|';
            }
        }
    }


    public Point allocate(char type) {
        Point locate = new Point();
        locate.x = -1; // Default to -1 if no spot is found
        locate.y = -1;

        for (int j = 2; j < 31; j += 4) {
            for (int k = 1; k < 29; k += 2) {
                if (mat[j][k] == ' ') { // Check for empty spot
                    mat[j][k] = type; // Mark the spot as occupied
                    locate.x = (j - 2) / 4 + 1; // Calculate x coordinate
                    locate.y = (k - 1) / 2 + 1; // Calculate y coordinate
                    return locate;
                }
            }
        }
        return locate; // Return (-1, -1) if no spot was found
    }

    public boolean remove(Point locate) {
        if (mat[locate.x][locate.y] == ' ') {
            return false; // No vehicle found at this location
        }
        mat[locate.x][locate.y] = ' '; // Clear the spot
        return true; // Vehicle removed successfully
    }

    public void display() {
        for (int j = 0; j < 32; j++) {
            for (int k = 0; k < 32; k++) {
                System.out.print(mat[j][k] + " ");
            }
            System.out.println();
        }
    }
}
