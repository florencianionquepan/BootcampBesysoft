package com.besysoft.ejercitacion.dominio;

import java.util.ArrayList;

public class Personaje {
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    private ArrayList <Pelicula> listaPelis;

    public Personaje() {
    }

    public Personaje(String nombre, int edad, double peso, String historia) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
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

    public ArrayList<Pelicula> getListaPelis() {
        return listaPelis;
    }

    public void setListaPelis(ArrayList<Pelicula> listaPelis) {
        this.listaPelis = listaPelis;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", peso=" + peso +
                ", historia='" + historia + '\'' +
                ", listaPelis=" + listaPelis +
                '}';
    }
}
