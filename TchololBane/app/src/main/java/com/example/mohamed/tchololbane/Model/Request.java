package com.example.mohamed.tchololbane.Model;

import java.util.List;

public class Request {
    private  String phone;
    private  String name;
    private  String address;
    private  String price;
    private List<Order> foodsInCart;

    public Request() {
    }

    public Request(String phone, String name, String address, String price, List<Order> foodsInCart) {
        this.phone = phone;
        this.name = name;
        this.address = address;
        this.foodsInCart = foodsInCart;
        this.price = price;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Order> getFoodsInCart() {
        return foodsInCart;
    }

    public void setFoodsInCart(List<Order> foodsInCart) {
        this.foodsInCart = foodsInCart;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
