package com.besysoft.bootcampspringboot.excepciones;

public class SocioNotFoundException extends Exception{

    public SocioNotFoundException(String message) {
        super(message);
    }

    public SocioNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}
