package com.vehicle.Parking;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
public class User {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="User_id")
private int user_id;
@Column(name="Username",unique = true)
private String username;
@Column(name="Password")
private String password; 
@Column(name="email",unique = true)
private String email;
@Column(name="phone_number")
private String phone_number;
@OneToMany
private List<Ticket> tickets;
@OneToMany
private List<ParkingLot> parkingLots;


public User( String username, String password, String email, String phone_number) {
	super();
	this.username = username;
	this.password = password;
	this.email = email;
	this.phone_number = phone_number;
}


public User() {
	super();
}


public int getUser_id() {
	return user_id;
}

public void setUser_id(int user_id) {
	this.user_id = user_id;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPhone_number() {
	return phone_number;
}
public void setPhone_number(String phone_number) {
	this.phone_number = phone_number;
}

public List<Ticket> getTickets() {
	return tickets;
}
public void setTickets(List<Ticket> tickets) {
	this.tickets = tickets;
}
public List<ParkingLot> getParkingLots() {
	return parkingLots;
}
public void setParkingLots(List<ParkingLot> parkingLots) {
	this.parkingLots = parkingLots;
}
@Override
public String toString() {
	return "User [user_id=" + user_id + ", username=" + username + ", password=" + password + ", email=" + email
			+ ", phone_number=" + phone_number + ", tickets=" + tickets + ", parkingLots=" + parkingLots + "]";
}

	    
	}


