package com.stock.stock.mapper;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.spring.api.DBRider;
import com.stock.stock.entity.Stock;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DBRider
@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class StockMapperTest {

    @Autowired
    StockMapper stockListMapper;

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 全ての在庫情報が取得できること() {
        List<Stock> stockList = stockListMapper.findAll();
        assertThat(stockList)
                .hasSize(8)
                .contains(
                        new Stock(1, "メタノール", "HPLC用", 3, "L", LocalDate.of(2023, 5, 24)),
                        new Stock(2, "塩化カリウム", "特級", 500, "g", LocalDate.of(2023, 7, 19)),
                        new Stock(3, "硫酸ナトリウム", "特級", 5, "kg", LocalDate.of(2022, 8, 30)),
                        new Stock(4, "グルコアミラーゼ", "生化学用", 10000, "unit", LocalDate.of(2023, 10, 11)),
                        new Stock(5, "硫酸", "硫酸呈色用", 500, "mL", LocalDate.of(2023, 4, 5)),
                        new Stock(6, "ピリドキシン塩酸塩", "日本薬局方標準品", 200, "mg", LocalDate.of(2023, 9, 22)),
                        new Stock(7, "亜硫酸ナトリウム", "特級", 25, "g", LocalDate.of(2023, 7, 7)),
                        new Stock(8, "アミド硫酸", "認証標準物質", 50, "g", LocalDate.of(2023, 4, 15)));
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 在庫名を指定したときに該当する在庫情報が取得できること() {
        List<Stock> stockList = stockListMapper.findByName("メタノール");
        assertThat(stockList)
                .contains(new Stock(1, "メタノール", "HPLC用", 3, "L", LocalDate.of(2023, 5, 24)));
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在しない在庫名を指定したときに空のリストが返されること() {
        List<Stock> stockList = stockListMapper.findByName("硝酸");
        assertThat(stockList).isEmpty();
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void idを指定したときに該当する在庫情報が取得できること() {
        Optional<Stock> stockList = stockListMapper.findById(1);
        assertThat(stockList)
                .contains(new Stock(1, "メタノール", "HPLC用", 3, "L", LocalDate.of(2023, 5, 24)));
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在しないidを指定したときに空のOptionalが返されること() {
        Optional<Stock> stockList = stockListMapper.findById(99);
        assertThat(stockList).isEmpty();
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 新しい在庫情報が登録できること() {
        Stock stock = new Stock(null, "トルエン", "特級", 100, "mL", LocalDate.of(2024, 2, 18));
        stockListMapper.insert(stock);

        // 結果確認用
        List<Stock> stocks = stockListMapper.findAll();
        assertThat(stocks).hasSize(9);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void idを指定したときに該当する在庫情報が更新できること() {
        Stock stock = new Stock(1, "メタノール", "特級", 3, "L", LocalDate.of(2023, 5, 24));
        stockListMapper.update(stock);

        // 結果確認用
        Optional<Stock> stockList = stockListMapper.findById(1);
        assertThat(stockList)
                .contains(new Stock(1, "メタノール", "特級", 3, "L", LocalDate.of(2023, 5, 24)));
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在しないidを指定したときに在庫情報が更新されないこと() {
        Stock stock = new Stock(99, "メタノール", "特級", 3, "L", LocalDate.of(2023, 5, 24));
        stockListMapper.update(stock);

        // 結果確認用
        Optional<Stock> stockList = stockListMapper.findById(1);
        assertThat(stockList)
                .contains(new Stock(1, "メタノール", "HPLC用", 3, "L", LocalDate.of(2023, 5, 24)));
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void idを指定したときに該当する在庫情報が削除できること() {
        stockListMapper.delete(1);

        // 結果確認用
        List<Stock> stocks = stockListMapper.findAll();
        assertThat(stocks).hasSize(7);
    }

    @Test
    @DataSet(value = "datasets/stockList.yml")
    @Transactional
    void 存在しないidを指定したときに在庫情報が削除されないこと() {
        stockListMapper.delete(99);

        // 結果確認用
        List<Stock> stocks = stockListMapper.findAll();
        assertThat(stocks).hasSize(8);
    }
}
