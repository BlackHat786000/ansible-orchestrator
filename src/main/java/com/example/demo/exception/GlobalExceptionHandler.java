package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception) {

		List<FieldError> errors = exception.getFieldErrors();
        List<String> message = new ArrayList<>();

        for (FieldError e : errors){
            message.add(e.getDefaultMessage());
        }

		ErrorResponse error = ErrorResponse.builder()
				.status(HttpStatus.BAD_REQUEST)
				.message(message.toString())
				.fix("Please provide a valid request body")
				.build();

		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(error);
    }

}
