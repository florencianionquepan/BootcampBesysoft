package com.besysoft.ejercitacion.controlador.handlerException;

import com.besysoft.ejercitacion.dto.response.ExceptionDTO;
import com.besysoft.ejercitacion.excepciones.ExistException;
import com.besysoft.ejercitacion.excepciones.ListaIncorrectaException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice(annotations = RestController.class)
public class ApiControllerAdvice {

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO exceptionHandler(MethodArgumentNotValidException ex){
        List<FieldError> errorLIst= ex.getBindingResult().getFieldErrors();
        Map<String,String> detalle= new HashMap<>();
        errorLIst.forEach(e->detalle.put(e.getField(),e.getDefaultMessage()));
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(),"Validaciones",detalle);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO isExist(ExistException ex){
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),null);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDTO listaIncorrecta(ListaIncorrectaException ex){
        return new ExceptionDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),null);
    }
    //VER COMO QUEDARIA SI TENGO UN SOLO EXISTEXCEPTION Y USO ESE PARA PELICULA Y GENERO
}
