package com.besysoft.ejercitacion.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

public class Genero {
    private int id;
    private String nombre;
    private List <Pelicula> listaPelis;
    public Genero() {
    }
    public Genero(int id, String nombre, List<Pelicula> listaPelis) {
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
    public List<Pelicula> getListaPelis() {
        return listaPelis;
    }
    public void setListaPelis(List<Pelicula> listaPelis) {
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
