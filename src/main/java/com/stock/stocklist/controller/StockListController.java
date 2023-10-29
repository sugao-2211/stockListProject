package com.stock.stocklist.controller;

import com.stock.stocklist.entity.StockList;
import com.stock.stocklist.service.StockListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class StockListController {

    private final StockListService stockListService;

    public StockListController(StockListService stockListService) {
        this.stockListService = stockListService;
    }

    @GetMapping("/stockList")
    public ResponseEntity<List<StockList>> allData() {
        List<StockList> getData = stockListService.allData();
        return ResponseEntity.ok(getData);
    }

    @GetMapping("/stockList")
    public ResponseEntity<List<StockList>> partData(@RequestParam(required = false) String name) {
        List<StockList> getData = stockListService.partData(name);
        return ResponseEntity.ok(getData);
    }

}
