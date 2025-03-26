package com.karoldm.bacen_service.excetions;

public class KeyAlreadyExistException extends RuntimeException {
    public KeyAlreadyExistException(String message){
        super(message);
    }
}
