package com.karoldm.account_service.configuration;

import com.karoldm.account_service.exception.AccountAlreadyExistsException;
import com.karoldm.account_service.exception.AccountNotFoundException;
import com.karoldm.account_service.exception.ErrorBacenIntegrationException;
import com.karoldm.account_service.exception.InsufficientBalanceException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler  {

    @ExceptionHandler(ErrorBacenIntegrationException.class)
    private ProblemDetail errorBacenIntegrationExceptionHandler(ErrorBacenIntegrationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
        problemDetail.setTitle("Bacen integration error");
        problemDetail.setType(URI.create("http://localhost/9000/doc/bacen-integration-error"));
        return problemDetail;
    }

    @ExceptionHandler(AccountNotFoundException.class)
    private ProblemDetail accountNotFoundExceptionHandler(AccountNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not found");
        problemDetail.setType(URI.create("http://localhost/9000/doc/not-found"));
        return problemDetail;
    }

    @ExceptionHandler(AccountAlreadyExistsException.class)
    private ProblemDetail accountAlreadyExisExceptiontsHandler(AccountAlreadyExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Conflict");
        problemDetail.setType(URI.create("http://localhost/9000/doc/conflict"));
        return problemDetail;
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    private ProblemDetail insufficientBalanceExceptionHandler(InsufficientBalanceException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.UNPROCESSABLE_ENTITY, ex.getMessage());
        problemDetail.setTitle("Insufficient balance");
        problemDetail.setType(URI.create("http://localhost/9000/doc/insufficient-balance"));
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Error no DTO");
        problemDetail.setType(URI.create("http://localhost/9000/doc/error-dto"));
        problemDetail.setDetail(errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}
