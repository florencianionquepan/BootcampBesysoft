package com.besysoft.ejercitacion.excepciones;

public class GeneroExistException extends RuntimeException{

    public GeneroExistException(String message) {
        super(message);
    }

    public GeneroExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
