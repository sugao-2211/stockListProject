package com.stock.stocklist.mapper;

import com.stock.stocklist.entity.Stock;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Optional;

@Mapper
public interface StockMapper {

    @Select("SELECT * FROM stock_list")
    List<Stock> findAll();

    @Select("SELECT * FROM stock_list WHERE name LIKE CONCAT('%',#{name},'%')")
    List<Stock> findByName(String name);

    @Select("SELECT * FROM stock_list WHERE id = #{id}")
    Optional<Stock> findById(int id);

    @Insert("INSERT INTO stock_list (name,grade,quantity,unit,purchase) VALUES (#{name},#{grade},#{quantity},#{unit},#{purchase})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(Stock stocklist);

    @Update("UPDATE stock_list SET name = #{name}, grade = #{grade}, quantity = #{quantity}, unit = #{unit}, purchase = #{purchase} WHERE id = #{id}")
    void update(Stock stockList);

    @Delete("DELETE FROM stock_list WHERE id = #{id}")
    void delete(Integer id);

}
