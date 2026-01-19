package com.sangspringproject.SANGSpringProject.models;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")   // match DB
	private int cartId;
	
	@Column(name = "created_at", updatable = false, insertable = false)
	private Date createdAt;

    @OneToOne
    @JoinColumn(name = "user_id")  // maps to your User primary key
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<CartProduct> cartProducts = new ArrayList<>();

    // Getters and Setters
    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }
}
