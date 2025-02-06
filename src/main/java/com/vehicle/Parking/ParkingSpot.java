package com.vehicle.Parking;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "parking_spot")
public class ParkingSpot {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="Spot_id")
private Long spot_id;
@Column(name="Spot_Number")
private int spot_number;
@Column(name="Status")
private boolean availability_status;

public Long getSpot_id() {
	return spot_id;
}

public void setSpot_id(Long spot_id) {
	this.spot_id = spot_id;
}

public int getSpot_number() {
	return spot_number;
}

public void setSpot_number(int spot_number) {
	this.spot_number = spot_number;
}

public boolean isAvailability_status() {
	return availability_status;
}

public ParkingSpot(Long spot_id, int spot_number, boolean availability_status, ParkingLot parkingLot) {
	super();
	this.spot_id = spot_id;
	this.spot_number = spot_number;
	this.availability_status = availability_status;
	this.parkingLot = parkingLot;
}

@Override
public String toString() {
	return "ParkingSpot [spot_id=" + spot_id + ", spot_number=" + spot_number + ", availability_status="
			+ availability_status + ", parkingLot=" + parkingLot + "]";
}

public void setAvailability_status(boolean availability_status) {
	this.availability_status = availability_status;
}

public ParkingLot getParkingLot() {
	return parkingLot;
}

public void setParkingLot(ParkingLot parkingLot) {
	this.parkingLot = parkingLot;
}

@ManyToOne
@JoinColumn(name = "lot_id")
private ParkingLot parkingLot;

}