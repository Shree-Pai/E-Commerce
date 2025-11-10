//package com.scem.ecommerce.entity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "products")
//public class Product {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long productId;
//
//    @Column(nullable = false)
//    private String name;
//
//    private String imageURL;
//
//    @Column(nullable = false)
//    private String description;
//
//    private Integer quantity;
//
//    private Double price;
//    private Double discount;
//    private Double specialPrice;
//
//    @ManyToOne
//    @JoinColumn(name = "category_id", nullable = false)
//    @JsonIgnore
//    private Category category;
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<CartItem> cartItems = new ArrayList<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<OrderItem> orderItems = new ArrayList<>();
//
//    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore
//    private List<Review> reviews = new ArrayList<>();
//
//    // Constructors
//    public Product() {}
//
//    public Product(String name, String imageURL, String description, Integer quantity, Double price) {
//        this.name = name;
//        this.imageURL = imageURL;
//        this.description = description;
//        this.quantity = quantity;
//        this.price = price;
//    }
//
//    // Getters and Setters
//    public Long getProductId() {
//        return productId;
//    }
//    public void setProductId(Long productId) {
//        this.productId = productId;
//    }
//
//    public String getName() {
//        return name;
//    }
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getImageURL() {
//        return imageURL;
//    }
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public Double getPrice() {
//        return price;
//    }
//    public void setPrice(Double price) {
//        this.price = price;
//    }
//
//    public Double getDiscount() {
//        return discount;
//    }
//    public void setDiscount(Double discount) {
//        this.discount = discount;
//    }
//
//    public Double getSpecialPrice() {
//        return specialPrice;
//    }
//    public void setSpecialPrice(Double specialPrice) {
//        this.specialPrice = specialPrice;
//    }
//
//    public Category getCategory() {
//        return category;
//    }
//    public void setCategory(Category category) {
//        this.category = category;
//    }
//
//    public List<CartItem> getCartItems() {
//        return cartItems;
//    }
//    public void setCartItems(List<CartItem> cartItems) {
//        this.cartItems = cartItems;
//    }
//
//    public List<OrderItem> getOrderItems() {
//        return orderItems;
//    }
//    public void setOrderItems(List<OrderItem> orderItems) {
//        this.orderItems = orderItems;
//    }
//
//    public List<Review> getReviews() {
//        return reviews;
//    }
//    public void setReviews(List<Review> reviews) {
//        this.reviews = reviews;
//    }
//
//    @Override
//    public String toString() {
//        return "Product [productId=" + productId + ", name=" + name + ", price=" + price + ", discount=" + discount
//                + ", specialPrice=" + specialPrice + "]";
//    }
//}

package com.scem.ecommerce.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String name;

    private String imageURL;

    @Column(nullable = false, length = 1000)
    private String description;

    // quantity / stock
    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private Double price;

    // simple category string (not a separate entity)
    private String category;

    // rating optional
    private Double rating;

    // keep relationships minimal: cartItems and orderItems optional (can remove if you want)
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public Product() {}

    public Product(String name, String imageURL, String description, Integer stock, Double price, String category) {
        this.name = name;
        this.imageURL = imageURL;
        this.description = description;
        this.stock = stock;
        this.price = price;
        this.category = category;
        this.rating = 0.0;
    }

    // getters & setters

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageURL() {
        return imageURL;
    }
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }
    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    @Override
    public String toString() {
        return "Product [productId=" + productId + ", name=" + name + ", price=" + price + ", stock=" + stock + "]";
    }
}

