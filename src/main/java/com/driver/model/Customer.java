package com.driver.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int customerId;

    private String mobile;
    private String password;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    List<TripBooking> tripBookingList = new ArrayList<>();

    public Customer() {
    }

    public Customer(int customerId, String mobile, String password, List<TripBooking> tripBookingList) {
        this.customerId = customerId;
        this.mobile = mobile;
        this.password = password;
        this.tripBookingList = tripBookingList;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<TripBooking> getTripBookingList() {
        return tripBookingList;
    }

    public void setTripBookingList(List<TripBooking> tripBookingList) {
        this.tripBookingList = tripBookingList;
    }
}