package com.gistimpact.carbonfootprint.advice;


import com.gistimpact.carbonfootprint.exception.CompanyNotFoundException;
import com.gistimpact.carbonfootprint.exception.ImpactDataNotfound;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@RestControllerAdvice
public class ExceptionHandler{
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @org.springframework.web.bind.annotation.ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String,String > invalidArgument(MethodArgumentNotValidException e){
        Map<String,String > errorMap=new HashMap<>();
        e.getBindingResult().getFieldErrors().forEach(error->{
            errorMap.put(error.getField(),error.getDefaultMessage());
        });
        return errorMap;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(CompanyNotFoundException.class)
    public ResponseEntity<Map<String,String >> companyNotFound(CompanyNotFoundException ex){
        Map<String,String > errorMap=new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return new ResponseEntity<>(errorMap,HttpStatus.NOT_FOUND);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handle(ConstraintViolationException exception) {

        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuilder builder = new StringBuilder();
        Map<String,String > errorMap=new HashMap<>();
        for (ConstraintViolation<?> violation : violations) {

            builder.append(violation.getMessage());
            builder.append("\n");
        }
        return new ResponseEntity<>(builder.toString(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @org.springframework.web.bind.annotation.ExceptionHandler(ImpactDataNotfound.class)
    public ResponseEntity<Map<String,String >> impactDataNotFound(ImpactDataNotfound ex){
        Map<String,String > errorMap=new HashMap<>();
        errorMap.put("errorMessage",ex.getMessage());
        return new ResponseEntity<>(errorMap,HttpStatus.NOT_FOUND);
    }

}
