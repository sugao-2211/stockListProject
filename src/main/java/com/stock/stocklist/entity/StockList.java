package com.stock.stocklist.entity;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class StockList {

    private int id;
    private String name;
    private String grade;
    private int quantity;
    private String unit;
    private LocalDate purchase;

    public StockList(int id, String name, String grade, int quantity, String unit, LocalDate purchase) {
        this.id = id;
        this.name = name;
        this.grade = grade;
        this.quantity = quantity;
        this.unit = unit;
        this.purchase = purchase;
    }

}
