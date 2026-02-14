package com.qingyunyouxiao.fsld.exceptions;

import com.qingyunyouxiao.fsld.dtos.ErrorDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class RestExceptionHandler {
 
    @ExceptionHandler(value = { AppException.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleAppException(AppException ex) {
        return ResponseEntity
                .status(ex.getStatus())
                .body(new ErrorDto(ex.getMessage()));
    }
 
    @ExceptionHandler(value = { Exception.class })
    @ResponseBody
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity
                .internalServerError()
                .body(new ErrorDto("Unknown error"));
    }
}