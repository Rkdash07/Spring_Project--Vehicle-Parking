package com.vehicle.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.vehicle.Parking.ParkingSpot;

public class ParkingSpotDao {

	 private Session session;

	    public ParkingSpotDao(Session session) {
	        this.session = session;
	    }

	    public void saveParkingSpot(ParkingSpot parkingSpot) {
	        try {
	            session.beginTransaction();
	            session.merge(parkingSpot);
	            session.getTransaction().commit();
	            session.clear();
	        } catch (HibernateException e) {
	            System.out.println("Hibernate exception: " + e);
	        } catch (Exception e) {
	            System.out.println("Exception: " + e);
	        }
	    }

	    public List<ParkingSpot> findAll() {
	        return session.createQuery("from ParkingSpot", ParkingSpot.class).list();
	    }
}
