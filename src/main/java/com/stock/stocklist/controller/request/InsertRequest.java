package com.stock.stocklist.controller.request;

import com.stock.stocklist.entity.StockList;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
public class InsertRequest {
    private String name;
    private String grade;
    private int quantity;
    private String unit;
    private LocalDate purchase;

    public StockList convertToStockList() {
        StockList convert = new StockList(null, name, grade, quantity, unit, purchase);
        return convert;
    }

}
