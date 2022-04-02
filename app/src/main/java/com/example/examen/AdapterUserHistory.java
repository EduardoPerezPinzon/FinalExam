package com.example.examen;

import java.util.Date;

public class AdapterUserHistory {
    private Date date;

    public AdapterUserHistory(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}