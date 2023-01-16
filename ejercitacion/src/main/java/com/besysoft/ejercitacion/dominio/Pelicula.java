package com.besysoft.ejercitacion.dominio;

import java.time.LocalDate;
import java.util.ArrayList;

public class Pelicula {
    private int id;
    private String titulo;
    private LocalDate fechaCreacion;
    private int calificacion;
    private ArrayList<Personaje> listaPersonajes;

    public Pelicula() {
    }

    public Pelicula(int id, String titulo, LocalDate fechaCreacion, int calificacion, ArrayList<Personaje> listaPersonajes) {
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.listaPersonajes = listaPersonajes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public LocalDate getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDate fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public ArrayList<Personaje> getListaPersonajes() {
        return listaPersonajes;
    }

    public void setListaPersonajes(ArrayList<Personaje> listaPersonajes) {
        this.listaPersonajes = listaPersonajes;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "titulo='" + titulo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", calificacion=" + calificacion +
                ", listaPersonajes=" + listaPersonajes +
                '}';
    }
}
