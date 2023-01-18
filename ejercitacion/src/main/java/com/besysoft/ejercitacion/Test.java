package com.besysoft.ejercitacion;
import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Test {
    public ArrayList<Personaje> listaPerso=new ArrayList<Personaje>();
    public ArrayList<Pelicula> listaPelis=new ArrayList<Pelicula>();
    public ArrayList<Genero> listaGeneros=new ArrayList<Genero>();

    public Test() {
    }

    public ArrayList<Personaje> getListaPerso() {
        return listaPerso;
    }

    public void setListaPerso(ArrayList<Personaje> listaPerso) {
        this.listaPerso = listaPerso;
    }

    public ArrayList<Pelicula> getListaPelis() {
        return listaPelis;
    }

    public void setListaPelis(ArrayList<Pelicula> listaPelis) {
        this.listaPelis = listaPelis;
    }

    public ArrayList<Genero> getListaGeneros() {
        return listaGeneros;
    }

    public void setListaGeneros(ArrayList<Genero> listaGeneros) {
        this.listaGeneros = listaGeneros;
    }

    public void generarDatos(){

        //--------Creo listas de personajes vacias--------
        ArrayList<Personaje> listaPersCoco=new ArrayList<>();
        ArrayList<Personaje> listaPersUp=new ArrayList<>();
        ArrayList<Personaje> listaPersPrimer=new ArrayList<>();


        //------Creo peliculas--------
        Pelicula peliCoco=new Pelicula(1,"Coco", LocalDate.of(2017,12,1),5,listaPersCoco);
        Pelicula peliUp=new Pelicula(2,"Up",LocalDate.of(2009,10,15),5,listaPersUp);
        Pelicula peliPrimer=new Pelicula(3,"Primer",LocalDate.of(2004,02,22),5,listaPersPrimer);


        //-----Creo Personajes de peliculas------
        Personaje persoCoco1=new Personaje(1,"Miguel",11,30,
                "Sueña con convertirse en un cantante famoso como su ídolo Ernesto de la Cruz, " +
                        "quien alcanzó la fama gracias a su tema 'Recuérdame'", peliCoco);
        Personaje persoCoco2=new Personaje(2,"Coco",80,75,
                " Es una cálida, apacible y tierna mujer que es la bisabuela de Miguel. ",peliCoco);
        Personaje persoUp1=new Personaje(3,"Carl",80,75,
                "Desde que era niño sueña con ser como Charles Muntz, un gran explorador.",peliUp);
        Personaje persoUp2=new Personaje(4,"Russel",8,35,
                "Es un niño explorador que quiere obtener la insignia " +
                        "de ayudar a los mayores en su grupo de boyscouts.",peliUp);
        Personaje persoPrimer1=new Personaje(5,"Aaron",26,58,"Trabaja durante el dia en una empresa " +
                "y por la noche realiza experimentos con sus amigos",peliPrimer);
        Personaje persoPrimer2=new Personaje(6,"Abe",27,57,
                "Es un ingeniero amigo de Aaron que descubre que crearon accidentalmente una maquina del tiempo",peliPrimer);

        Collections.addAll(listaPerso,persoCoco1,persoCoco2,persoUp1,persoUp2,persoPrimer1,persoPrimer2);
        this.setListaPerso(listaPerso);

        //Asocio los personajes a cada lista:
        listaPersCoco.add(persoCoco1);
        listaPersCoco.add(persoCoco2);
        listaPersUp.add(persoUp1);
        listaPersUp.add(persoUp2);
        listaPersPrimer.add(persoPrimer1);
        listaPersPrimer.add(persoPrimer2);

        //Seteo las listas a cada objeto pelicula
        peliCoco.setListaPersonajes(listaPersCoco);
        peliUp.setListaPersonajes(listaPersUp);
        peliPrimer.setListaPersonajes(listaPersPrimer);

        Collections.addAll(listaPelis,peliCoco,peliUp,peliPrimer);
        this.setListaPelis(listaPelis);

        //-----Generos------
        ArrayList<Pelicula> pelisInfan=new ArrayList<>();
        ArrayList<Pelicula> pelisFiccion=new ArrayList<>();
        pelisInfan.add(peliUp);
        pelisInfan.add(peliCoco);
        pelisFiccion.add(peliPrimer);
        Genero infantil=new Genero(1,"Infantil",pelisInfan);
        Genero cienciaFiccion=new Genero(2,"CienciaFiccion",pelisFiccion);

        Collections.addAll(listaGeneros,infantil,cienciaFiccion);
        this.setListaGeneros(listaGeneros);
    }

}
