package com.stock.stock.service;

import com.stock.stock.entity.Stock;
import com.stock.stock.exception.NotFoundException;
import com.stock.stock.mapper.StockMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @InjectMocks
    StockService stockListService;

    @Mock
    StockMapper stockListMapper;

    @Test
    public void 存在する在庫のIDを指定したときに正常に在庫の情報が返されること() throws Exception {
        doReturn(Optional.of(new Stock(1, "メタノール", "HPLC用", 3, "L", LocalDate.parse("2023-05-24"))))
                .when(stockListMapper).findById(1);

        Stock actual = stockListService.findById(1);
        assertThat(actual).isEqualTo(new Stock(1, "メタノール", "HPLC用", 3, "L", LocalDate.parse("2023-05-24")));
        verify(stockListMapper, times(1)).findById(1);
    }

    @Test
    public void 存在しないIDを指定したときにNotFoundExceptionが返されること() throws Exception {
        doReturn(Optional.empty()).when(stockListMapper).findById(99);
        assertThrows(NotFoundException.class, () -> {
            stockListService.findById(99);
        });
        verify(stockListMapper, times(1)).findById(99);
    }

    @Test
    public void 在庫の名前を指定しなかったときにfindAllメソッド呼び出されて全件の在庫情報が返却されること() throws Exception {
        List<Stock> stockList = List.of(
                new Stock(1, "メタノール", "HPLC用", 3, "L", LocalDate.parse("2023-05-24")),
                new Stock(2, "塩化カリウム", "特級", 500, "g", LocalDate.parse("2023-07-19")),
                new Stock(3, "硫酸ナトリウム", "特級", 5, "kg", LocalDate.parse("2023-10-11"))
        );

        doReturn(stockList).when(stockListMapper).findAll();

        List<Stock> actual = stockListService.findData(null);
        assertThat(actual).isEqualTo(stockList);
        verify(stockListMapper, times(1)).findAll();
        verify(stockListMapper, never()).findByName("メタノール");

    }

    @Test
    public void 存在する名前を指定したときにfindByNameメソッドが呼び出されて該当する在庫情報が返却されること() throws Exception {
        doReturn(List.of(new Stock(1, "メタノール", "HPLC用", 3, "L", LocalDate.parse("2023-05-24"))))
                .when(stockListMapper).findByName("メタノール");

        List<Stock> actual = stockListService.findData("メタノール");

        assertThat(actual).isEqualTo(List.of(new Stock(1, "メタノール", "HPLC用", 3, "L", LocalDate.parse("2023-05-24"))));
        verify(stockListMapper, times(1)).findByName("メタノール");
        verify(stockListMapper, never()).findAll();
    }

    @Test
    public void 存在しない名前を指定したときに空のListが返されること() throws Exception {
        doReturn(Collections.emptyList()).when(stockListMapper).findByName("硝酸");

        List<Stock> actual = stockListService.findData("硝酸");
        assertThat(actual).isEqualTo(Collections.emptyList());

        verify(stockListMapper, times(1)).findByName("硝酸");
        verify(stockListMapper, never()).findAll();
    }

    @Test
    public void 新しい在庫情報が正常に登録処理が実行されること() throws Exception {
        Stock stock = new Stock(null, "アセトニトリル", "HPLC用", 3, "L", LocalDate.parse("2024-05-13"));
        doNothing().when(stockListMapper).insert(stock);

        Stock actual = stockListService.insert(stock);
        assertThat(actual).isEqualTo(stock);
        verify(stockListMapper, times(1)).insert(stock);
    }
}
