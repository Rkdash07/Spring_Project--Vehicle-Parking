package com.vehicle.Parking;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_lots")
public class ParkingLot {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="Lot_id")
private Long lot_id;
@Column(name="Name")
private String name;
@Column(name="Location")
private String location;
@Column(name="Capacity")
private int capacity;
    
@OneToMany
private List<ParkingSpot> parkingSpots;

@ManyToOne
@JoinColumn(name = "user_id")
private User user;
public ParkingLot(Long lot_id, String name, String location, int capacity, List<ParkingSpot> parkingSpots, User user) {
	super();
	this.lot_id = lot_id;
	this.name = name;
	this.location = location;
	this.capacity = capacity;
	this.parkingSpots = parkingSpots;
	this.user = user;
}

public Long getLot_id() {
	return lot_id;
}

public void setLot_id(Long lot_id) {
	this.lot_id = lot_id;
}

public String getName() {
	return name;
}

public void setName(String name) {
	this.name = name;
}

public String getLocation() {
	return location;
}

public void setLocation(String location) {
	this.location = location;
}

public int getCapacity() {
	return capacity;
}

public void setCapacity(int capacity) {
	this.capacity = capacity;
}

public List<ParkingSpot> getParkingSpots() {
	return parkingSpots;
}

public void setParkingSpots(List<ParkingSpot> parkingSpots) {
	this.parkingSpots = parkingSpots;
}

public User getUser() {
	return user;
}

public void setUser(User user) {
	this.user = user;
}
@Override
public String toString() {
	return "ParkingLot [lot_id=" + lot_id + ", name=" + name + ", location=" + location + ", capacity=" + capacity
			+ ", parkingSpots=" + parkingSpots + ", user=" + user + "]";
}
    
}
