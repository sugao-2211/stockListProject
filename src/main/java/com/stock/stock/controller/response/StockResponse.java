package com.stock.stock.controller.response;

import com.stock.stock.entity.Stock;

import java.time.LocalDate;

public class StockResponse {
    private int id;
    private String name;
    private String grade;
    private int quantity;
    private String unit;
    private LocalDate purchase;

    public StockResponse(Stock stock) {
        this.id = stock.getId();
        this.name = stock.getName();
        this.grade = stock.getGrade();
        this.quantity = stock.getQuantity();
        this.unit = stock.getUnit();
        this.purchase = stock.getPurchase();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    //3桁区切りで表示する処理を記載
    public String getQuantity() {
        String thousandsSeparator = String.format("%,d", quantity);
        return thousandsSeparator;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getPurchase() {
        return purchase;
    }

}
