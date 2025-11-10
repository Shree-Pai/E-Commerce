//package com.scem.ecommerce.entity;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.scem.ecommerce.entity.enums.RoleName;
//
//import jakarta.persistence.CascadeType;
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.FetchType;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.JoinTable;
//import jakarta.persistence.ManyToMany;
//import jakarta.persistence.OneToMany;
//import jakarta.persistence.OneToOne;
//import jakarta.persistence.Table;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//
//@Entity
//@Table(name = "users")
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userId;
//
//    private String firstName;
//    private String lastName;
//
//    @Size(min = 10, max = 10, message = "Mobile Number must be exactly 10 digits long")
//    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number must contain only Numbers")
//    private String mobileNumber;
//
//    @Email
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String password;
//
//    @ManyToMany(fetch = FetchType.EAGER)
//    @JoinTable(
//        name = "user_role",
//        joinColumns = @JoinColumn(name = "user_id"),
//        inverseJoinColumns = @JoinColumn(name = "role_id"))
//    
//    private Set<Role> roles = new HashSet<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Address> addresses = new ArrayList<>();
//
//    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
//    @JsonIgnore
//    private Cart cart;
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Order> orders = new ArrayList<>();
//
//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<Review> reviews = new ArrayList<>();
//
//    public User() {
//    }
//
//    public User(String firstName, String lastName, String mobileNumber, String email, String password) {
//        super();
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.mobileNumber = mobileNumber;
//        this.email = email;
//        this.password = password;
//    }
//
//    public Long getUserId() {
//        return userId;
//    }
//
//    public void setUserId(Long userId) {
//        this.userId = userId;
//    }
//
//    public String getFirstName() {
//        return firstName;
//    }
//
//    public void setFirstName(String firstName) {
//        this.firstName = firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public void setLastName(String lastName) {
//        this.lastName = lastName;
//    }
//
//    public String getMobileNumber() {
//        return mobileNumber;
//    }
//
//    public void setMobileNumber(String mobileNumber) {
//        this.mobileNumber = mobileNumber;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Set<Role> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(Set<Role> roles) {
//        this.roles = roles;
//    }
//
//    public Cart getCart() {
//        return cart;
//    }
//
//    public void setCart(Cart cart) {
//        this.cart = cart;
//    }
//
//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }
//
//    public List<Review> getReviews() {
//        return reviews;
//    }
//
//    public void setReviews(List<Review> reviews) {
//        this.reviews = reviews;
//    }
//
//    public List<Address> getAddresses() {
//        return addresses;
//    }
//
//    public void setAddresses(List<Address> addresses) {
//        this.addresses = addresses;
//    }
//
//    /** Utility Methods **/
//    public void addRole(Role role) {
//        this.roles.add(role);
//        role.getUsers().add(this);
//    }
//
//    public void addAddress(Address address) {
//        addresses.add(address);
//        address.setUser(this);
//    }
//
//    public void removeAddress(Address address) {
//        addresses.remove(address);
//        address.setUser(null);
//    }
//
//    public void addOrder(Order order) {
//        this.orders.add(order);
//        order.setUser(this);
//    }
//
//    public void addReview(Review review) {
//        this.reviews.add(review);
//        review.setUser(this);
//    }
//    public boolean hasRole(RoleName roleName) {
//        if (roles == null) return false;
//        return roles.stream()
//                .anyMatch(role -> role.getRoleName() == roleName);
//    }
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (!(o instanceof User)) return false;
//        User that = (User) o;
//        return Objects.equals(getUserId(), that.getUserId());
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(getUserId());
//    }
//
//    @Override
//    public String toString() {
//        return "User [userId=" + userId + ", email=" + email + "]";
//    }
//}

package com.scem.ecommerce.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.scem.ecommerce.entity.enums.RoleName;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String firstName;
    private String lastName;

    @Size(min = 10, max = 10, message = "Mobile Number must be exactly 10 digits long")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number must contain only Numbers")
    private String mobileNumber;

    @Email
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @OneToOne(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Order> orders = new ArrayList<>();

    // removed addresses & reviews fields (they referenced deleted entities)

    public User() {
    }

    public User(String firstName, String lastName, String mobileNumber, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
    }

    // getters & setters for existing fields only (omit addresses & reviews)

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Set<Role> getRoles() { return roles; }
    public void setRoles(Set<Role> roles) { this.roles = roles; }

    public Cart getCart() { return cart; }
    public void setCart(Cart cart) { this.cart = cart; }

    public List<Order> getOrders() { return orders; }
    public void setOrders(List<Order> orders) { this.orders = orders; }

    /** Utility Methods **/
    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void addOrder(Order order) {
        this.orders.add(order);
        order.setUser(this);
    }

    public boolean hasRole(RoleName roleName) {
        if (roles == null) return false;
        return roles.stream().anyMatch(role -> role.getRoleName() == roleName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User that = (User) o;
        return Objects.equals(getUserId(), that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId());
    }

    @Override
    public String toString() {
        return "User [userId=" + userId + ", email=" + email + "]";
    }
}

