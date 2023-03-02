package com.besysoft.bootcampspringboot.Controller.handlerException;

import com.besysoft.bootcampspringboot.excepciones.SocioExistException;
import com.besysoft.bootcampspringboot.negocio.dto.response.ExceptionDto;
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
    public ExceptionDto exceptionHandler(MethodArgumentNotValidException ex){
        List<FieldError> errorList= ex.getBindingResult().getFieldErrors();
        Map<String,String> detalle= new HashMap<>();
        errorList.forEach(e->detalle.put(e.getField(), e.getDefaultMessage()));
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), "Validaciones", detalle);
    }

    @ExceptionHandler
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionDto isExist(SocioExistException ex){
        return new ExceptionDto(HttpStatus.BAD_REQUEST.value(), ex.getMessage(),null);
    }
}
