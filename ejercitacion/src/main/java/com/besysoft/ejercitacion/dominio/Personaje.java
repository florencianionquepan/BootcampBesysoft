package com.besysoft.ejercitacion.dominio;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;

public class Personaje {
    private int id;
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    @JsonIgnoreProperties(value="listaPersonajes")
    private Pelicula pelicula;

    public Personaje() {
    }


    public Personaje(int id, String nombre, int edad, double peso, String historia, Pelicula pelicula) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        this.pelicula = pelicula;
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

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "nombre='" + nombre + '\'' +
                '}';
    }
}
