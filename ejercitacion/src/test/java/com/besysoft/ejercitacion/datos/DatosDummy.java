package com.besysoft.ejercitacion.datos;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;

import java.time.LocalDate;
import java.util.ArrayList;

public class DatosDummy {

    public static Genero getGeneroTerror(){
        return new Genero(0,"Terror",new ArrayList<Pelicula>());
    }

    public static Genero getGeneroDrama(){
        return new Genero(0,"Drama",new ArrayList<Pelicula>());
    }

    public static Pelicula getPeliculaCoco(){
        return new Pelicula(1,"Coco", LocalDate.of(2017,12,1),
                4, new ArrayList<Personaje>());
    }

    public static Pelicula getPeliculaUp(){
        return new Pelicula(2,"Up",LocalDate.of(2009,10,15),
                2,new ArrayList<Personaje>());
    }

    public static Personaje getPersonajeMiguel(){
        return new Personaje(1,"Miguel",11,30,
                "Sueña con convertirse en un cantante famoso", new ArrayList<Pelicula>());
    }

    public static Personaje getPersonajeCarl(){
        return new Personaje(3,"Carl",80, 75.0,
                "Desde que era niño sueña con ser un gran explorador.", new ArrayList<Pelicula>());
    }
}
