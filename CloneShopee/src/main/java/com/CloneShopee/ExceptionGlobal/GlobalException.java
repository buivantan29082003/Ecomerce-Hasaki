package com.CloneShopee.ExceptionGlobal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.CloneShopee.ResponeEntity.BaseRespone;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String errorMessage = "";
        System.out.println("dcdjcdkjcd");
        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            errorMessage += fieldError.getField() + "-" + fieldError.getDefaultMessage();
            break;
        }
        return new ResponseEntity<>(new BaseRespone(null, errorMessage.toString()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintException.class)
    public ResponseEntity<Object> handleValidationExceptions(ConstraintException ex) {

        return new ResponseEntity<>(new BaseRespone("Field: " + ex.getField(), "- Lỗi: " + ex.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleValidationExceptions(Exception ex) {

        return new ResponseEntity<>(new BaseRespone(null, ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
