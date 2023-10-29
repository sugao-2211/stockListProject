package com.stock.stocklist.service;

import com.stock.stocklist.entity.StockList;
import com.stock.stocklist.mapper.StockListMapper;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Getter
public class StockListService {

    private final StockListMapper stockListMapper;

    public StockListService(StockListMapper stockListMapper) {
        this.stockListMapper = stockListMapper;
    }

    public List<StockList> findAll() {
        return stockListMapper.findAll();
    }

    public Optional<StockList> findById(int id) {
        return stockListMapper.findById(id);
    }

}
