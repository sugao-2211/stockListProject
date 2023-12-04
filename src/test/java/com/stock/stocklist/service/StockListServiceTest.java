package com.stock.stocklist.service;

import com.stock.stocklist.entity.StockList;
import com.stock.stocklist.exception.NotFoundException;
import com.stock.stocklist.mapper.StockListMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class StockListServiceTest {

    @InjectMocks
    StockListService stockListService;

    @Mock
    StockListMapper stockListMapper;

    @Test
    public void 存在する在庫のIDを指定したときに正常に在庫の情報が返されること() throws Exception {
        doReturn(Optional.of(new StockList(1, "メタノール", "HPLC用", 3, "L", LocalDate.parse("2023-05-24"))))
                .when(stockListMapper).findById(1);

        StockList actual = stockListService.findById(1);
        assertThat(actual).isEqualTo(new StockList(1, "メタノール", "HPLC用", 3, "L", LocalDate.parse("2023-05-24")));
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
    public void 存在しないIDを指定し例外が発生したときに期待した例外ハンドリングが返されること() throws Exception {
        doThrow(new NotFoundException("data not found")).when(stockListMapper).findById(99);

        assertThatThrownBy(() -> stockListService.findById(99)).isInstanceOfSatisfying(NotFoundException.class, e -> assertThat(e.getMessage()).isEqualTo("data not found"));
        verify(stockListMapper, times(1)).findById(99);
    }

}
