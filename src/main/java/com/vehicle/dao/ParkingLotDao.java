package com.vehicle.dao;


import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.vehicle.Parking.ParkingLot;

public class ParkingLotDao {

	private Session session;

    public ParkingLotDao(Session session) {
        this.session = session;
    }

    public void saveParkingLot(ParkingLot parkingLot) {
        try {
            session.beginTransaction();
            session.merge(parkingLot);
            session.getTransaction().commit();
            session.clear();
        } catch (HibernateException e) {
            System.out.println("Hibernate exception: " + e);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public List<ParkingLot> findAll() {
        return session.createQuery("from ParkingLot", ParkingLot.class).list();
    }
}
