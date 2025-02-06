package com.vehicle.dao;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import com.vehicle.Parking.User;

import jakarta.persistence.Query;

public class UserDao {

    private Session session;

    public UserDao(Session session) {
        this.session = session;
    }

    public void saveUser(User user) {
        try {
            session.beginTransaction();
            session.merge(user);
            session.getTransaction().commit();
            session.clear();
        } catch (HibernateException e) {
            System.out.println("Hibernate exception: " + e);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public List<User> findAll() {
        return session.createQuery("from User", User.class).list();
    }

    public User getUserByUsername(String username) {
        return session.createQuery("FROM User WHERE username=:username", User.class)
                .setParameter("username", username).uniqueResult();
    }
}



