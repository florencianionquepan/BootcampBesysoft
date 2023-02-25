package com.besysoft.bootcampspringboot.datos;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DatosDummy {

    public static Socio getSocioUno(){
        return new Socio(null,"Socio uno");
    }

    public static Socio getSocioDos(){
        return new Socio(null,"Socio dos");
    }

    public static Socio getSocioTres(){
        return new Socio(null,"Socio tres");
    }

    public static List<Socio> getSocios(){
        return new ArrayList<>(Arrays.asList(
                new Socio(1l,"Socio Uno"),
                new Socio(2L,"Socio Dos")
        ));
    }
}
