package com.stock.stocklist.service;

import com.stock.stocklist.entity.StockList;
import com.stock.stocklist.mapper.StockListMapper;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Getter
public class StockListService {

    private final StockListMapper stockListMapper;

    public StockListService(StockListMapper stockListMapper) {
        this.stockListMapper = stockListMapper;
    }

    public List<StockList> allData() {
        return stockListMapper.findAll();
    }

    public List<StockList> partData(String name) {
        return stockListMapper.findByName(name);
    }

}
