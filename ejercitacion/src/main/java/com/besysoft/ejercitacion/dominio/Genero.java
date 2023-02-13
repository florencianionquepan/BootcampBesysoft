package com.besysoft.ejercitacion.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="generos")
public class Genero implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 15)
    private String nombre;
    @OneToMany(mappedBy = "genero")
    //@JsonIgnoreProperties(value="genero")
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

                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Genero)) return false;
        Genero genero = (Genero) o;
        return getId() == genero.getId() && Objects.equals(getNombre(), genero.getNombre());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre());
    }
}
