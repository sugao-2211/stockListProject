package com.stock.stocklist.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private HttpStatus status;
    private String message;
    private List<Map<String, String>> errors;

}
