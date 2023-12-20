package com.stock.stocklist.service;

import com.stock.stocklist.entity.Stock;
import com.stock.stocklist.exception.NotFoundException;
import com.stock.stocklist.mapper.StockMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockMapper stockListMapper;

    public List<Stock> findData(String name) {
        List<Stock> stockList;
        if (Objects.isNull(name)) {
            stockList = stockListMapper.findAll();
        } else {
            stockList = stockListMapper.findByName(name);
        }
        return stockList;
    }

    public Stock findById(int id) {
        return stockListMapper.findById(id).orElseThrow(() -> new NotFoundException("data not found"));
    }

    public Stock insert(Stock stock) {
        stockListMapper.insert(stock);
        return stock;
    }

    public Stock update(Stock stock) {
        Optional<Stock> updatedStock = stockListMapper.findById(stock.getId());
        if (updatedStock.isPresent()) {
            stockListMapper.update(stock);
        } else {
            throw new NotFoundException("data not found");
        }
        return stock;
    }

    public Integer delete(Integer id) {
        Optional<Stock> deletedStock = stockListMapper.findById(id);
        if (deletedStock.isPresent()) {
            stockListMapper.delete(id);
        } else {
            throw new NotFoundException("data not found");
        }
        return id;
    }

}
