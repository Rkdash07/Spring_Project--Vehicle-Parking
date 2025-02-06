package com.vehicle.Parking;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tickets")
public class Ticket {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="ticket_id")
private int ticket_id;
@Column(name="issue_time")
private LocalDateTime issue_time;
@Column(name="exit_time")
private LocalDateTime exit_time;
@Column(name="fee_paid")
private double fee_paid;

@ManyToOne
@JoinColumn(name = "vehicle_id")
private Vehicle vehicle;
@ManyToOne
@JoinColumn(name = "spot_id")
private ParkingSpot parkingSpot;
public int getTicket_id() {
	return ticket_id;
}
public void setTicket_id(int ticket_id) {
	this.ticket_id = ticket_id;
}
public LocalDateTime getIssue_time() {
	return issue_time;
}
public void setIssue_time(LocalDateTime issue_time) {
	this.issue_time = issue_time;
}
public LocalDateTime getExit_time() {
	return exit_time;
}
public void setExit_time(LocalDateTime exit_time) {
	this.exit_time = exit_time;
}
public double getFee_paid() {
	return fee_paid;
}
public void setFee_paid(double fee_paid) {
	this.fee_paid = fee_paid;
}
public Vehicle getVehicle() {
	return vehicle;
}
public void setVehicle(Vehicle vehicle) {
	this.vehicle = vehicle;
}
public ParkingSpot getParkingSpot() {
	return parkingSpot;
}
public void setParkingSpot(ParkingSpot parkingSpot) {
	this.parkingSpot = parkingSpot;
}
@Override
public String toString() {
	return "Ticket [ticket_id=" + ticket_id + ", issue_time=" + issue_time + ", exit_time=" + exit_time
			+ ", fee_paid=" + fee_paid + ", vehicle=" + vehicle + ", parkingSpot=" +  "]";
}
public Ticket(int ticket_id, LocalDateTime issue_time, LocalDateTime exit_time, double fee_paid, Vehicle vehicle) {
	super();
	this.ticket_id = ticket_id;
	this.issue_time = issue_time;
	this.exit_time = exit_time;
	this.fee_paid = fee_paid;
	this.vehicle = vehicle;
	
}
   
}
