package com.sangspringproject.SANGSpringProject.models;

import javax.persistence.*;
import java.util.*;
import java.util.Date;

@Entity
@Table(name = "orders")   // don't use 'order' (it's a reserved SQL word)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // ✅ Custom Order Number
    @Column(name = "order_number", unique = true, nullable = false)
    private String orderNumber;

    private String fullname;
    private String address;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_id")
    private String paymentId;

    private String status = "Preparing for shipping"; // ✅ default status

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Date createdAt = new Date();

    // ✅ Relation with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // ✅ Relation with Cart (optional snapshot)
    @OneToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "total_amount", nullable = false)
    private double totalAmount;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    // === Utility method to generate custom order number ===
    public void generateOrderNumber() {
        // Ensures 6-digit number, starting from 100000
        this.orderNumber = String.valueOf(100000 + this.id);
    }

    // === Getters and Setters ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public String getFullname() { return fullname; }
    public void setFullname(String fullname) { this.fullname = fullname; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Date getCreatedAt() { return createdAt; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
}
