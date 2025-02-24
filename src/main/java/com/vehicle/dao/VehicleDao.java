package com.vehicle.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.vehicle.Parking.Vehicle;

public class VehicleDao {

	private Session session;

    public VehicleDao(Session session) {
        this.session = session;
    }

    public void saveVehicle(Vehicle vehicle) {
        try {
            session.beginTransaction();
            session.merge(vehicle);
            session.getTransaction().commit();
            session.clear();
        } catch (HibernateException e) {
            System.out.println("Hibernate exception: " + e);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public List<Vehicle> findAll() {
        return session.createQuery("from Vehicle", Vehicle.class).list();
    }
    public void removeVehicleByLicensePlate(String license_plate_no) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            Query<Vehicle> query = session.createQuery("FROM Vehicle WHERE license_plate_no = :license_plate_no", Vehicle.class);
            query.setParameter("license_plate_no", license_plate_no);
            Vehicle vehicle = query.uniqueResult();

            if (vehicle != null) {
                session.delete(vehicle);
                System.out.println("Vehicle with license plate " + license_plate_no + " has been removed.");
            } else {
                System.out.println("No vehicle found with license plate " + license_plate_no);
            }

            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

