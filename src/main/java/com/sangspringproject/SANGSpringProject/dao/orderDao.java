package com.sangspringproject.SANGSpringProject.dao;

import com.sangspringproject.SANGSpringProject.models.Order;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class orderDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void saveOrder(Order order) {
        entityManager.merge(order);
    }

    public Order getOrderById(int id) {
        return entityManager.find(Order.class, id);
    }

    public List<Order> getOrdersByUser(int userId) {
        return entityManager.createQuery("FROM Order o WHERE o.user.id = :userId", Order.class)
                .setParameter("userId", userId)
                .getResultList();
    }
    
    public long countAllOrders() {
        return entityManager.createQuery("SELECT COUNT(o) FROM Order o", Long.class)
                .getSingleResult();
    }
    
    public Order getOrderWithItems(int id) {
        return entityManager.createQuery(
            "SELECT o FROM Order o " +
            "LEFT JOIN FETCH o.orderItems " +
            "WHERE o.id = :id",
            Order.class
        )
        .setParameter("id", id)
        .getSingleResult();
    }

    
}
