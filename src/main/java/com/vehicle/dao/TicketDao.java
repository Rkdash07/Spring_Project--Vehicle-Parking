package com.vehicle.dao;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.vehicle.Parking.Ticket;

public class TicketDao {

	private Session session;

    public TicketDao(Session session) {
        this.session = session;
    }

    public void saveTicket(Ticket ticket) {
        try {
            session.beginTransaction();
            session.merge(ticket);
            session.getTransaction().commit();
            session.clear();
        } catch (HibernateException e) {
            System.out.println("Hibernate exception: " + e);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public List<Ticket> findAll() {
        return session.createQuery("from Ticket", Ticket.class).list();
    }
}
