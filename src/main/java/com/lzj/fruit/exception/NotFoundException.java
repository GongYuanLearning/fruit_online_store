package com.lzj.fruit.exception;

public class NotFoundException extends PersistentException {
    public NotFoundException(String message) {
        super(message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
