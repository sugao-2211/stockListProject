package com.stock.stocklist.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.stock.stocklist.entity.StockList;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StockListMapperTest {

    @Autowired
    StockListMapper stockListMapper;

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 全ての在庫情報が取得できること() {
        List<StockList> stockList = stockListMapper.findAll();
        assertThat(stockList)
                .hasSize(6)
                .contains(
                        new StockList(1, "メタノール", "HPLC用", 3, "L", LocalDate.of(2023, 5, 24)),
                        new StockList(2, "塩化カリウム", "特級", 500, "g", LocalDate.of(2023, 7, 19)),
                        new StockList(3, "硫酸ナトリウム", "特級", 5, "kg", LocalDate.of(2022, 8, 30)),
                        new StockList(4, "グルコアミラーゼ", "生化学用", 10000, "unit", LocalDate.of(2023, 10, 11)),
                        new StockList(5, "硫酸", "硫酸呈色用", 500, "ｍL", LocalDate.of(2023, 4, 5)),
                        new StockList(6, "ピリドキシン塩酸塩", "日本薬局方標準品", 200, "mg", LocalDate.of(2023, 9, 22))
                );
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫名を指定したときに該当する在庫情報が取得できること() {
        List<StockList> stockList = stockListMapper.findByName("メタノール");
        assertThat(stockList)
                .contains(new StockList(1, "メタノール", "HPLC用", 3, "L", LocalDate.of(2023, 5, 24)));
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在しない在庫名を指定したときに空のリストが返されること() {
        List<StockList> stockList = stockListMapper.findByName("硝酸");
        assertThat(stockList).isEmpty();
    }

}
