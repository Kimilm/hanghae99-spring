package com.sparta.selectshop.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class RestApiException {
    private String errorMessge;
    private HttpStatus httpStatus;
}
