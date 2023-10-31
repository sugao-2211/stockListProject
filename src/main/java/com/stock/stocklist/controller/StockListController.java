package com.stock.stocklist.controller;

import com.stock.stocklist.controller.response.ThousandsSeparatorResponse;
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
    public ResponseEntity<List<ThousandsSeparatorResponse>> findData(String name) {
        List<StockList> getData = stockListService.findData(name);
        List<ThousandsSeparatorResponse> findData = getData.stream().map(ThousandsSeparatorResponse::new).toList();
        return ResponseEntity.ok(findData);
    }

    @GetMapping("/stockList/{id}")
    public ResponseEntity<List<ThousandsSeparatorResponse>> partData(@PathVariable int id) {
        Optional<StockList> getData = stockListService.findById(id);
        List<ThousandsSeparatorResponse> partData = getData.stream().map(ThousandsSeparatorResponse::new).toList();
        return ResponseEntity.ok(partData);
    }

}
