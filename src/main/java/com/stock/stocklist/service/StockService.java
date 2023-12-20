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

    private final StockMapper stockMapper;

    public List<Stock> findData(String name) {
        List<Stock> stockList;
        if (Objects.isNull(name)) {
            stockList = stockMapper.findAll();
        } else {
            stockList = stockMapper.findByName(name);
        }
        return stockList;
    }

    public Stock findById(int id) {
        return stockMapper.findById(id).orElseThrow(() -> new NotFoundException("data not found"));
    }

    public Stock insert(Stock stock) {
        stockMapper.insert(stock);
        return stock;
    }

    public Stock update(Stock stock) {
        Optional<Stock> updatedStock = stockMapper.findById(stock.getId());
        if (updatedStock.isPresent()) {
            stockMapper.update(stock);
        } else {
            throw new NotFoundException("data not found");
        }
        return stock;
    }

    public Integer delete(Integer id) {
        Optional<Stock> deletedStock = stockMapper.findById(id);
        if (deletedStock.isPresent()) {
            stockMapper.delete(id);
        } else {
            throw new NotFoundException("data not found");
        }
        return id;
    }

}
