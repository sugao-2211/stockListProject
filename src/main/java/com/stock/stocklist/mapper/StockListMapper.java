package com.stock.stocklist.mapper;

import com.stock.stocklist.entity.StockList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface StockListMapper {

    @Select("select * from stock_list WHERE name LIKE CONCAT('%',#{name},'%') ")
    List<StockList> findByName(String name);
}
