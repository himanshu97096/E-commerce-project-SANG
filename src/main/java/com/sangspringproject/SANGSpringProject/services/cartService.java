package com.sangspringproject.SANGSpringProject.services;

import com.sangspringproject.SANGSpringProject.dao.cartDao;
import com.sangspringproject.SANGSpringProject.dao.cartProductDao;
import com.sangspringproject.SANGSpringProject.models.*;
import org.springframework.transaction.annotation.Transactional;

import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cartService {

    @Autowired
    private cartDao cartDao;

    @Autowired
    private cartProductDao cartProductDao;

    public void addToCart(User user, Product product) {
        Cart cart = cartDao.getCartByUser(user);

        // Check if product already exists in cart
        for (CartProduct cp : cart.getCartProducts()) {
            if (cp.getProduct().getId() == product.getId()) {
                cp.setQuantity(cp.getQuantity() + 1);
                cartDao.saveCart(cart);
                return;
            }
        }

        // Add new product to cart
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCart(cart);
        cartProduct.setProduct(product);
        cartProduct.setQuantity(1);

        cart.getCartProducts().add(cartProduct);
        cartDao.saveCart(cart);
    }
    
    public Cart getCartByUser(User user) {
        Cart cart = cartDao.getCartByUser(user);
        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
            cartDao.saveCart(cart);
        }
        return cart;
    }

    @Transactional
    public void removeFromCart(int cartProductId) {
        CartProduct cp = cartProductDao.getById(cartProductId);

        if (cp != null) {
            Cart cart = cp.getCart();

            // 🔑 First remove the association from parent
            if (cart != null) {
                cart.getCartProducts().removeIf(p -> p.getId() == cartProductId);
            }

            // 🔑 Then delete the child entity
            cartProductDao.delete(cp);
        }
    }
    
    public void updateQuantity(int cartProductId, int delta) {
        CartProduct cp = cartProductDao.getById(cartProductId);
        if (cp == null) return;

        Cart cart = cp.getCart();

        int newQty = cp.getQuantity() + delta;

        if (newQty <= 0) {
            // just remove from collection, orphanRemoval will delete it
            cart.getCartProducts().remove(cp);
            cartDao.saveCart(cart);
        } else {
            cp.setQuantity(newQty);
            cartProductDao.save(cp); // update quantity
        }
    }
    
    @Transactional
    public void clearCart(User user) {
        Cart cart = cartDao.getCartByUser(user);
        if (cart != null) {
            cart.getCartProducts().clear();
            cartDao.saveCart(cart);
        }
    }
}
