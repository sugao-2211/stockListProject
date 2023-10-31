package com.stock.stocklist.controller;

import com.stock.stocklist.controller.response.StockListResponse;
import com.stock.stocklist.entity.StockList;
import com.stock.stocklist.exception.NotFoundException;
import com.stock.stocklist.service.StockListService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;

@RestController
public class StockListController {

    private final StockListService stockListService;

    public StockListController(StockListService stockListService) {
        this.stockListService = stockListService;
    }

    @GetMapping("/stockList")
    public ResponseEntity<List<StockListResponse>> findData(String name) {
        List<StockList> getData = stockListService.findData(name);
        List<StockListResponse> findData = getData.stream().map(StockListResponse::new).toList();
        return ResponseEntity.ok(findData);
    }

    @GetMapping("/stockList/{id}")
    public ResponseEntity<StockListResponse> partData(@PathVariable int id) {
        StockList getData = stockListService.findById(id);
        StockListResponse partData = new StockListResponse(getData);
        return ResponseEntity.ok(partData);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<Map<String, String>> handleNoResourceFound(
            NotFoundException e, HttpServletRequest request) {
        Map<String, String> body = Map.of(
                "timestamp", ZonedDateTime.now().toString(),
                "status", String.valueOf(HttpStatus.NOT_FOUND.value()),
                "error", HttpStatus.NOT_FOUND.getReasonPhrase(),
                "message", e.getMessage(),
                "path", request.getRequestURI());
        return new ResponseEntity(body, HttpStatus.NOT_FOUND);
    }

}
