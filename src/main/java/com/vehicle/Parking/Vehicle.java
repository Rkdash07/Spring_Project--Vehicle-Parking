package com.vehicle.Parking;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "V_id") 
    private int vehicle_id;

    @Column(name = "License")
    private String license_plate_no;

    @Column(name = "Type")
    private String type;

    @Column(name = "Owner_Name")
    private String owner_name;

    @Column(name = "Color")
    private String color;

    // Getters and Setters
    public int getVehicle_id() {
        return vehicle_id;
    }

    public void setVehicle_id(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }

    public String getLicense_plate_no() {
        return license_plate_no;
    }

    public void setLicense_plate_no(String license_plate_no) {
        this.license_plate_no = license_plate_no;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "Vehicle [vehicle_id=" + vehicle_id + ", license_plate_no=" + license_plate_no + ", type=" + type
                + ", owner_name=" + owner_name + ", color=" + color + "]";
    }

    // Constructors
    public Vehicle(int vehicle_id, String license_plate_no, String type, String owner_name, String color) {
        super();
        this.vehicle_id = vehicle_id;
        this.license_plate_no = license_plate_no;
        this.type = type;
        this.owner_name = owner_name;
        this.color = color;
    }

    public Vehicle() {
        // Default constructor
    }
}
