package com.sangspringproject.SANGSpringProject.models;

import javax.persistence.*;

@Entity
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // ✅ relation to Order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    // ✅ relation to Product (keeps reference to original product)
    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    // ✅ snapshot values (so even if product changes, order details stay consistent)
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_image")
    private String productImage;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double price; // unit price at the time of order

    @Column(name = "total_price", nullable = false)
    private double totalPrice; // quantity * price

    // === Constructors ===
    public OrderItem() {}

    public OrderItem(Order order, Product product, int quantity, double price) {
        this.order = order;
        this.product = product;
        this.productName = product.getName();
        this.productImage = product.getImage();
        this.quantity = quantity;
        this.price = price;
        this.totalPrice = quantity * price;
    }

    // === Getters & Setters ===
    // id
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    // order
    public Order getOrder() { return order; }
    public void setOrder(Order order) { this.order = order; }

    // product
    public Product getProduct() { return product; }
    public void setProduct(Product product) { this.product = product; }

    // snapshots
    public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }

    public String getProductImage() { return productImage; }
    public void setProductImage(String productImage) { this.productImage = productImage; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.totalPrice = this.price * quantity;
    }

    public double getPrice() { return price; }
    public void setPrice(double price) {
        this.price = price;
        this.totalPrice = this.price * this.quantity;
    }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }
}
