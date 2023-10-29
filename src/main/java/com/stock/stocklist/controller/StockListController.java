package com.stock.stocklist.controller;

import com.stock.stocklist.entity.StockList;
import com.stock.stocklist.service.StockListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class StockListController {

    private final StockListService stockListService;

    public StockListController(StockListService stockListService) {
        this.stockListService = stockListService;
    }

    @GetMapping("/stockList")
    public ResponseEntity<List<StockList>> allData() {
        List<StockList> getData = stockListService.findAll();
        return ResponseEntity.ok(getData);
    }

    @GetMapping("/stockList/{id}")
    public ResponseEntity<Optional<StockList>> partData(@PathVariable int id) {
        Optional<StockList> getData = stockListService.findById(id);
        return ResponseEntity.ok(getData);
    }

}
