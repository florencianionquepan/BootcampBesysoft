package com.besysoft.ejercitacion.dominio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
import java.util.Objects;

public class Personaje {
    private int id;
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    @JsonIgnoreProperties(value="listaPersonajes")
    private List<Pelicula> listaPeliculas;

    public Personaje() {
    }

    public Personaje(int id, String nombre, int edad, double peso, String historia, List<Pelicula> listaPeliculas) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        this.listaPeliculas = listaPeliculas;
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getHistoria() {
        return historia;
    }

    public void setHistoria(String historia) {
        this.historia = historia;
    }

    public List<Pelicula> getListaPeliculas() {
        return listaPeliculas;
    }

    public void setListaPeliculas(List<Pelicula> listaPeliculas) {
        this.listaPeliculas = listaPeliculas;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "nombre='" + nombre + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personaje)) return false;
        Personaje personaje = (Personaje) o;
        return getId() == personaje.getId() && getEdad() == personaje.getEdad() && Double.compare(personaje.getPeso(), getPeso()) == 0 && Objects.equals(getNombre(), personaje.getNombre()) && Objects.equals(getHistoria(), personaje.getHistoria()) && Objects.equals(getListaPeliculas(), personaje.getListaPeliculas());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getEdad(), getPeso(), getHistoria(), getListaPeliculas());
    }
}
