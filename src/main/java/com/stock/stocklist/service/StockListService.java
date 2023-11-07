package com.stock.stocklist.service;

import com.stock.stocklist.entity.StockList;
import com.stock.stocklist.exception.NotFoundException;
import com.stock.stocklist.mapper.StockListMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StockListService {

    private final StockListMapper stockListMapper;

    public List<StockList> findData(String name) {
        List<StockList> getData;
        if (Objects.isNull(name)) {
            getData = stockListMapper.findAll();
        } else {
            getData = stockListMapper.findByName(name);
            if (getData.isEmpty()) {
                throw new NotFoundException("data not found");
            }
        }
        return getData;
    }

    public StockList findById(int id) {
        return stockListMapper.findById(id).orElseThrow(() -> new NotFoundException("data not found"));
    }

    public StockList insert(StockList stockList) {
        stockListMapper.insert(stockList);
        return stockList;
    }

    public StockList update(StockList stockList) {
        Optional<StockList> updatedStockList = stockListMapper.findById(stockList.getId());
        if (updatedStockList.isPresent()) {
            stockListMapper.update(stockList);
        } else {
            throw new NotFoundException("data not found");
        }
        return stockList;
    }

}
