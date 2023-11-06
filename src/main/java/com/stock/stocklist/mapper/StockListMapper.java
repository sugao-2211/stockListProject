package com.stock.stocklist.mapper;

import com.stock.stocklist.entity.StockList;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
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

    @Insert("insert into stock_list (name,grade,quantity,unit,purchase) values (#{name},#{grade},#{quantity},#{unit},#{purchase})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(StockList stocklist);

}
