package com.karoldm.bacen_service.configuration;


import com.karoldm.bacen_service.excetions.KeyAlreadyExistException;
import com.karoldm.bacen_service.excetions.KeyNotFoundException;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;

@RestControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(KeyAlreadyExistException.class)
    private ProblemDetail handleKeyAlreadyExistException(KeyAlreadyExistException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.CONFLICT, ex.getMessage());
        problemDetail.setTitle("Duplicated key");
        problemDetail.setType(URI.create("http://localhost:9090/document/key-already-exists"));
        return problemDetail;
    }

    @ExceptionHandler(KeyNotFoundException.class)
    private ProblemDetail handkeKeyNotFoundException(KeyNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Not found key");
        problemDetail.setType(URI.create("http://localhost:9090/document/key-not-found"));
        return problemDetail;
    }
}
