package com.besysoft.bootcampspringboot.excepciones;

public class SocioExistException extends RuntimeException{

    public SocioExistException(String message) {
        super(message);
    }

    //le vamos a indicat el motivo original de esta excepcion
    public SocioExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
