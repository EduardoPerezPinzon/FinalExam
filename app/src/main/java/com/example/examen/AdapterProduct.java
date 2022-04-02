package com.example.examen;

public class AdapterProduct {
    private String id;
    private String name;
    private String price;
    private String amount;

    public AdapterProduct(String id, String name, String price, String amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String user) {
        this.name = user;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
