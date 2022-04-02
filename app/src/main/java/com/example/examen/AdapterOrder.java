package com.example.examen;

public class AdapterOrder {
    private String id;
    private String productId;
    private String user;
    private Boolean status;
    private Integer amount;

    public AdapterOrder(String id, String productId, String user, Boolean status, Integer amount) {
        this.id = id;
        this.productId = productId;
        this.user = user;
        this.status = status;
        this.amount = amount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String userId) {
        this.user = user;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount (Integer amount) {
        this.amount = amount;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}