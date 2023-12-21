package com.stock.stock.entity;

import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@EqualsAndHashCode
public class Stock {

    private Integer id;
    private String name;
    private String grade;
    private int quantity;
    private String unit;
    private LocalDate purchase;

    public Stock(Integer id, String name, String grade, int quantity, String unit, LocalDate purchase) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.quantity = quantity;
        this.unit = unit;
        this.purchase = purchase;
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

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public LocalDate getPurchase() {
        return purchase;
    }
}
