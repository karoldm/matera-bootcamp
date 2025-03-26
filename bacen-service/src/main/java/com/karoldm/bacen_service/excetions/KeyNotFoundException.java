package com.karoldm.bacen_service.excetions;

public class KeyNotFoundException extends RuntimeException {
    public KeyNotFoundException(String message){
        super(message);
    }
}
