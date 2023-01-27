package com.besysoft.ejercitacion.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pelicula {
    private int id;
    private String titulo;
    private LocalDate fechaCreacion;
    private int calificacion;
    @JsonIgnoreProperties(value="pelicula")
    private List<Personaje> listaPersonajes;

    public Pelicula() {
    }

    public Pelicula(int id, String titulo, LocalDate fechaCreacion, int calificacion, List<Personaje> listaPersonajes) {
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

    public List<Personaje> getListaPersonajes() {
        return listaPersonajes;
    }

    public void setListaPersonajes(List<Personaje> listaPersonajes) {
        this.listaPersonajes = listaPersonajes;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id= "+id+
                " titulo='" + titulo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", calificacion=" + calificacion +
                ", listaPersonajes=" + listaPersonajes +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pelicula)) return false;
        Pelicula pelicula = (Pelicula) o;
        return getId() == pelicula.getId() && getCalificacion() == pelicula.getCalificacion() && Objects.equals(getTitulo(), pelicula.getTitulo()) && Objects.equals(getFechaCreacion(), pelicula.getFechaCreacion()) && Objects.equals(getListaPersonajes(), pelicula.getListaPersonajes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitulo(), getFechaCreacion(), getCalificacion(), getListaPersonajes());
    }
}
