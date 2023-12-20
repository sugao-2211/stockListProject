package com.stock.stocklist.controller.request;

import com.stock.stocklist.entity.Stock;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
public class InsertRequest {

    @NotBlank(message = "試薬名が入力されていません")
    @Length(min = 1, max = 100, message = "{min}文字以上、{max}文字以内で入力してください")
    private String name;

    @NotBlank(message = "等級が入力されていません")
    private String grade;

    @NotNull
    @Min(value = 1, message = "{value}以上の値を入力してください")
    private int quantity;

    @NotBlank(message = "単位が入力されていません")
    private String unit;

    @NotNull(message = "購入日が入力されていません")
    @PastOrPresent
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate purchase;

    public Stock convertToStock() {
        Stock convert = new Stock(null, name, grade, quantity, unit, purchase);
        return convert;
    }

}
