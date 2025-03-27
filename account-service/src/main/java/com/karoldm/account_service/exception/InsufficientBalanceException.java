package com.karoldm.account_service.exception;

public class InsufficientBalanceException extends RuntimeException {
    public InsufficientBalanceException(String message){
        super(message);
    }
}
