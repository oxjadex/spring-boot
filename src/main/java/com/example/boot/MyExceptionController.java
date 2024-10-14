package com.example.boot;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

//MyExceptionHandler.java
@ControllerAdvice
public class MyExceptionController {

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<String> handler() {
        return ResponseEntity.status(400).body("특정 에러시 발동");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handler1() {
        return ResponseEntity.status(400).body("에러남");
    }

}
