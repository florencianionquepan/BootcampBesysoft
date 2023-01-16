package com.besysoft.ejercitacion.dominio;

import java.util.ArrayList;

public class Genero {
    private String nombre;
    private ArrayList <Pelicula> listaPelis;

    public Genero(String nombre) {
        this.nombre = nombre;
    }
    public Genero() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Pelicula> getListaPelis() {
        return listaPelis;
    }

    public void setListaPelis(ArrayList<Pelicula> listaPelis) {
        this.listaPelis = listaPelis;
    }

    @Override
    public String toString() {
        return "Genero{" +
                "nombre='" + nombre + '\'' +
                ", listaPelis=" + listaPelis +
                '}';
    }
}
