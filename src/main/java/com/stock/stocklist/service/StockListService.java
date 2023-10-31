package com.stock.stocklist.service;

import com.stock.stocklist.entity.StockList;
import com.stock.stocklist.mapper.StockListMapper;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Getter
public class StockListService {

    private final StockListMapper stockListMapper;

    public StockListService(StockListMapper stockListMapper) {
        this.stockListMapper = stockListMapper;
    }

    public List<StockList> findData(String name) {
        List<StockList> getData;
        if (Objects.isNull(name)) {
            getData = stockListMapper.findAll();
        } else {
            getData = stockListMapper.findByName(name);
        }
        return getData;
    }

    public Optional<StockList> findById(int id) {
        return stockListMapper.findById(id);
    }

}
