package com.stock.stocklist.mapper;

import com.stock.stocklist.entity.StockList;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StockListMapper {

    @Select("select * from stock_list")
    List<StockList> findAll();

    @Select("select * from stock_list where name like concat('%',#{name},'%')")
    List<StockList> findByName(String name);

    @Select("select * from stock_list where id = #{id}")
    Optional<StockList> findById(int id);

}
