package com.stock.stocklist.controller;

import com.stock.stocklist.controller.request.InsertRequest;
import com.stock.stocklist.controller.request.UpdateRequest;
import com.stock.stocklist.controller.response.MessageResponse;
import com.stock.stocklist.controller.response.StockListResponse;
import com.stock.stocklist.entity.StockList;
import com.stock.stocklist.service.StockListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
public class StockListController {

    private final StockListService stockListService;

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

    @PostMapping("/stockList")
    public ResponseEntity<MessageResponse> insert(@RequestBody @Validated InsertRequest insertRequest, UriComponentsBuilder uriComponentsBuilder) {
        StockList insertData = stockListService.insert(insertRequest.convertToStockList());
        URI uri = uriComponentsBuilder.path("/stockList/{id}").buildAndExpand(insertData.getId()).toUri();
        MessageResponse message = new MessageResponse("newData Created");
        return ResponseEntity.created(uri).body(message);
    }

    @PatchMapping("/stockList/{id}")
    public ResponseEntity<MessageResponse> update(@PathVariable Integer id, @RequestBody UpdateRequest updateRequest) {
        StockList updateData = stockListService.update(updateRequest.convertToStockList(id));
        MessageResponse message = new MessageResponse("data Updated");
        return ResponseEntity.ok(message);
    }

}
