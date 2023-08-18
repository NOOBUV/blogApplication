package com.app.blog.exceptions;

import com.app.blog.payloads.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Map<String,String>> methodArgumentNotValidHandler(MethodArgumentNotValidException ex) {
        Map<String,String> resp = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            resp.put(fieldName,message);
        });

        return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ApiResponse> apiExceptionHandler(ApiException ex) {
        String message = ex.getMessage();
        ApiResponse apiResponse = new ApiResponse(message,false);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.UNAUTHORIZED);
    }

//    @ExceptionHandler(ImageTypeWrongException.class)
//    public ResponseEntity<ApiResponse>
}
