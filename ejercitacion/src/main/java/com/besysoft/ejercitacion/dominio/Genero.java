package com.besysoft.ejercitacion.dominio;

import java.util.ArrayList;

public class Genero {
    private int id;
    private String nombre;
    private ArrayList <Pelicula> listaPelis;

    public Genero() {
    }

    public Genero(int id, String nombre, ArrayList<Pelicula> listaPelis) {
        this.id = id;
        this.nombre = nombre;
        this.listaPelis = listaPelis;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
