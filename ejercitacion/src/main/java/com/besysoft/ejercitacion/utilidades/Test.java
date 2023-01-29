package com.besysoft.ejercitacion.utilidades;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Test {
    public static List<Personaje> listaPerso=new ArrayList<Personaje>();
    public static List<Pelicula> listaPelis=new ArrayList<Pelicula>();
    public static List<Genero> listaGeneros=new ArrayList<Genero>();

    public Test() {
    }

    public static List<Personaje> getListaPerso() {
        return listaPerso;
    }

    public void setListaPerso(List<Personaje> listaPerso) {
        this.listaPerso = listaPerso;
    }

    public static List<Pelicula> getListaPelis() {
        return listaPelis;
    }

    public void setListaPelis(List<Pelicula> listaPelis) {
        this.listaPelis = listaPelis;
    }

    public static List<Genero> getListaGeneros() {
        return listaGeneros;
    }

    public void setListaGeneros(List<Genero> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }

    public void generarDatos(){

        //--------Creo listas de personajes vacias--------
        List<Personaje> listaPersoCoco1=new ArrayList<Personaje>();
        List<Personaje> listaPersoCoco2=new ArrayList<Personaje>();
        List<Personaje> listaPersoPrimer=new ArrayList<Personaje>();

        //------Creo Listas de Peliculas--------
        Pelicula peliCoco=new Pelicula(1,"Coco", LocalDate.of(2017,12,1),4,listaPersoCoco1);
        Pelicula peliCoco2=new Pelicula(2,"Coco2",LocalDate.of(2009,10,15),3,listaPersoCoco2);
        Pelicula peliPrimer=new Pelicula(3,"Primer",LocalDate.of(2004,02,22),5,listaPersoPrimer);
        List<Pelicula> pelisCocoMiguel=new ArrayList<Pelicula>(
                Arrays.asList(peliCoco,peliCoco2)
        );
        List<Pelicula> pelisCocoCoco=new ArrayList<Pelicula>(
                Arrays.asList(peliCoco,peliCoco2)
        );
        List<Pelicula> pelisCocoCarl=new ArrayList<Pelicula>(
                Arrays.asList(peliCoco)
        );
        List<Pelicula> pelisCocoRu=new ArrayList<Pelicula>(
                Arrays.asList(peliCoco)
        );
        List<Pelicula> pelisPrimerAaron=new ArrayList<Pelicula>(
                Arrays.asList(peliPrimer)
        );
        List<Pelicula> pelisPrimerAbe=new ArrayList<Pelicula>(
                Arrays.asList(peliPrimer)
        );

        //-----Creo Personajes de peliculas------
        Personaje persoCoco1=new Personaje(1,"Miguel",11,30,
                "Sueña con convertirse en un cantante famoso como su ídolo Ernesto de la Cruz, " +
                        "quien alcanzó la fama gracias a su tema 'Recuérdame'", pelisCocoMiguel);
        Personaje persoCoco2=new Personaje(2,"Coco",80,75,
                " Es una cálida, apacible y tierna mujer que es la bisabuela de Miguel. ",pelisCocoCoco);
        Personaje persoCoco3=new Personaje(3,"Carl",80, 75.0,
                "Desde que era niño sueña con ser como Charles Muntz, un gran explorador.", pelisCocoCarl);
        Personaje persoCoco4=new Personaje(4,"Russel",8,35,
                "Es un niño explorador que quiere obtener la insignia " +
                        "de ayudar a los mayores en su grupo de boyscouts.",pelisCocoRu);
        Personaje persoPrimer1=new Personaje(5,"Aaron",26,58,"Trabaja durante el dia en una empresa " +
                "y por la noche realiza experimentos con sus amigos",pelisPrimerAaron);
        Personaje persoPrimer2=new Personaje(6,"Abe",27,57,
                "Es un ingeniero amigo de Aaron que descubre que crearon accidentalmente una maquina del tiempo",pelisPrimerAbe);

        Collections.addAll(listaPerso,persoCoco1,persoCoco2,persoCoco3,persoCoco4,persoPrimer1,persoPrimer2);
        this.setListaPerso(listaPerso);

        //Asocio los personajes a cada lista:
        Collections.addAll(listaPersoCoco1,persoCoco1,persoCoco2,persoCoco3,persoCoco4);
        Collections.addAll(listaPersoCoco2,persoCoco1,persoCoco2);
        Collections.addAll(listaPersoPrimer,persoPrimer1,persoPrimer2);

        //Seteo las listas a cada objeto pelicula
        peliCoco.setListaPersonajes(listaPersoCoco1);
        peliCoco2.setListaPersonajes(listaPersoCoco2);
        peliPrimer.setListaPersonajes(listaPersoPrimer);

        Collections.addAll(listaPelis,peliCoco,peliCoco2,peliPrimer);
        this.setListaPelis(listaPelis);

        //-----Generos------
        ArrayList<Pelicula> pelisInfan=new ArrayList<>();
        ArrayList<Pelicula> pelisFiccion=new ArrayList<>();
        pelisInfan.add(peliCoco);
        pelisInfan.add(peliCoco2);
        pelisFiccion.add(peliPrimer);
        Genero infantil=new Genero(1,"Infantil",pelisInfan);
        Genero cienciaFiccion=new Genero(2,"CienciaFiccion",pelisFiccion);

        Collections.addAll(listaGeneros,infantil,cienciaFiccion);
        this.setListaGeneros(listaGeneros);
    }

}
