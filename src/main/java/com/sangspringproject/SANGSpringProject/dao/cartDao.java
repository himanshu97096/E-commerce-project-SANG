package com.sangspringproject.SANGSpringProject.dao;

import com.sangspringproject.SANGSpringProject.models.Cart;
import com.sangspringproject.SANGSpringProject.models.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class cartDao {

    @PersistenceContext
    private EntityManager entityManager;

    public Cart getCartByUser(User user) {
        try {
            return entityManager.createQuery("SELECT c FROM Cart c WHERE c.user = :user", Cart.class)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (Exception e) {
            Cart cart = new Cart();
            cart.setUser(user);
            entityManager.persist(cart);
            return cart;
        }
    }

    public Cart saveCart(Cart cart) {
        if (cart.getCartId() == 0) {
        	entityManager.persist(cart);
            return cart;
        } else {
            return entityManager.merge(cart);
        }
    }
}
