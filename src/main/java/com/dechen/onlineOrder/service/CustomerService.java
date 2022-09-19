package com.dechen.onlineOrder.service;

import com.dechen.onlineOrder.dao.CustomerDao;
import com.dechen.onlineOrder.entity.Cart;
import com.dechen.onlineOrder.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    public void signUp(Customer customer) {
        // create new cart object for customer
        Cart cart = new Cart();
        customer.setCart(cart);

        customer.setEnabled(true);
        // call Dao to signup
        customerDao.signUp(customer);
    }

    public Customer getCustomer(String email) {
        return customerDao.getCustomer(email);
    }

}