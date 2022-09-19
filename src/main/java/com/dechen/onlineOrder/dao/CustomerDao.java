package com.dechen.onlineOrder.dao;

import com.dechen.onlineOrder.entity.Authorities;
import com.dechen.onlineOrder.entity.Customer;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void signUp(Customer customer) {
        Authorities authorities = new Authorities();
        authorities.setAuthorities("ROLE_USER");
        authorities.setEmail(customer.getEmail());

        // open session to add info for signup, roll back when fail
        // put all CURD inside transaction
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.save(authorities);
            session.save(customer);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
            if (session != null) session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close(); // need to close sesstion when done
            }
        }
    }

    // get customer
    public Customer getCustomer(String email) {
        Customer customer = null;
        // need to open session for request conflict
        try (Session session = sessionFactory.openSession()) {
            customer = session.get(Customer.class, email); // need class type and email
        } catch (Exception ex) {
            // no need for roll back for get
            ex.printStackTrace();
        }
        return customer;
    }
}
