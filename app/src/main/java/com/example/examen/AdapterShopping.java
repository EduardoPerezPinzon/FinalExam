package com.example.examen;

public class AdapterShopping {
    private String name;
    private String user;
    private String amount;
    private String price;

    public AdapterShopping(String name, String user, String amount, String price) {
        this.name = name;
        this.user = user;
        this.amount = amount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}