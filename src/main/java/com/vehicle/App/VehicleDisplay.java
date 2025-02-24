package com.vehicle.App;

import java.awt.Point;

import org.hibernate.Session;

interface intfmat {
    Point allocate(char type);
    boolean remove(Point locate);
    void display();
    char mat[][] = new char[16][16]; 
}

class VehicleDisplay implements intfmat {
    public char mat[][];
    VehicleDisplay() {
        mat = new char[16][16];
        initializeGrid(); 
    }
    public VehicleDisplay(Session session) {
        mat = new char[16][16];
        initializeGrid(); 
    }
    private void initializeGrid() {
        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                mat[j][k] = ' ';
            }
        }
        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                if (j == 0 || (j == 15 && k != 14 && k != 13 && k != 12)) {
                    mat[j][k] = '-';
                }
                if (k == 0 || k == 15) {
                    mat[j][k] = '|';
                }
            }
        }
        for (int j = 1; j < 15; j += 2) {
            for (int k = 1; k < 13; k++) {
                mat[j][k] = '-';
            }
        }
        for (int j = 2; j < 15; j += 4) {
            for (int k = 2; k < 13; k += 2) {
                mat[j][k] = '|';
            }
        }
    }
    public Point allocate(char type) {
        Point locate = new Point();
        locate.x = -1;
        locate.y = -1;
        for (int j = 2; j < 15; j += 4) {
            for (int k = 1; k < 13; k += 2) {
                if (mat[j][k] == ' ') { 
                    mat[j][k] = type; 
                    locate.x = (j - 2) / 4 + 1; 
                    locate.y = (k - 1) / 2 + 1; 
                    return locate;
                }
            }
        }
        return locate;
    }

    public boolean remove(Point locate) {
        if (mat[locate.x][locate.y] == ' ') {
            return false; 
        }
        mat[locate.x][locate.y] = ' '; 
        return true; 
    }

    public void display() {
        for (int j = 0; j < 16; j++) {
            for (int k = 0; k < 16; k++) {
                System.out.print(mat[j][k] + " ");
            }
            System.out.println();
        }
    }
}
