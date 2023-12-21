package com.stock.stock.controller;

import com.stock.stock.controller.request.InsertRequest;
import com.stock.stock.controller.request.UpdateRequest;
import com.stock.stock.controller.response.MessageResponse;
import com.stock.stock.controller.response.StockResponse;
import com.stock.stock.entity.Stock;
import com.stock.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/stock")
    public ResponseEntity<List<StockResponse>> findData(String name) {
        List<Stock> stock = stockService.findData(name);
        List<StockResponse> allData = stock.stream().map(StockResponse::new).toList();
        return ResponseEntity.ok(allData);
    }

    @GetMapping("/stock/{id}")
    public ResponseEntity<StockResponse> partData(@PathVariable int id) {
        Stock stock = stockService.findById(id);
        StockResponse partData = new StockResponse(stock);
        return ResponseEntity.ok(partData);
    }

    @PostMapping("/stock")
    public ResponseEntity<MessageResponse> insert(@RequestBody @Validated InsertRequest insertRequest, UriComponentsBuilder uriComponentsBuilder) {
        Stock stock = stockService.insert(insertRequest.convertToStock());
        URI uri = uriComponentsBuilder.path("/stockList/{id}").buildAndExpand(stock.getId()).toUri();
        MessageResponse message = new MessageResponse("new data created");
        return ResponseEntity.created(uri).body(message);
    }

    @PatchMapping("/stock/{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody @Validated UpdateRequest updateRequest) {
        stockService.update(updateRequest.convertToStock(id));
        MessageResponse message = new MessageResponse("data updated");
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/stock/{id}")
    public ResponseEntity<MessageResponse> delete(@PathVariable Integer id) {
        stockService.delete(id);
        MessageResponse message = new MessageResponse("data deleted");
        return ResponseEntity.ok(message);
    }

}
