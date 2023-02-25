package com.besysoft.bootcampspringboot.datos;

import com.besysoft.bootcampspringboot.entidades.oneToOne.Socio;

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
}
