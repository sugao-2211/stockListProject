package com.stock.stocklist.controller.response;

import com.stock.stocklist.entity.StockList;

import java.time.LocalDate;

public class ThousandsSeparatorResponse {
    private int id;
    private String name;
    private String grade;
    private int quantity;
    private String unit;
    private LocalDate purchase;

    public ThousandsSeparatorResponse(StockList stockList) {
        this.id = stockList.getId();
        this.name = stockList.getName();
        this.grade = stockList.getGrade();
        this.quantity = stockList.getQuantity();
        this.unit = stockList.getUnit();
        this.purchase = stockList.getPurchase();
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