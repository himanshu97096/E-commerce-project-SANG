package com.sangspringproject.SANGSpringProject.services;

import com.sangspringproject.SANGSpringProject.dao.orderDao;
import com.sangspringproject.SANGSpringProject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class orderService {

    @Autowired
    private orderDao orderDao;

    @Autowired
    private cartService cartService;

    public Order getOrderWithItems(int orderId) {
        return orderDao.getOrderWithItems(orderId);
    }
}
