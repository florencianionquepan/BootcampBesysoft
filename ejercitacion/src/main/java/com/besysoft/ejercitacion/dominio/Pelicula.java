package com.besysoft.ejercitacion.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
@Entity
@Table(name="peliculas")
public class Pelicula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 15)
    private String titulo;
    private LocalDate fechaCreacion;
    private int calificacion;
    @ManyToMany(mappedBy = "listaPeliculas", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JsonIgnoreProperties(value="listaPeliculas")
    private List<Personaje> listaPersonajes;

    @ManyToOne(fetch = FetchType.LAZY,
            cascade = {CascadeType.REFRESH,CascadeType.MERGE})
    @JoinColumn(name = "genero_id")
    @JsonIgnoreProperties(value="listaPelis")
    private Genero genero;

    public Genero getGenero() {
        return genero;
    }

    public void setGenero(Genero genero) {
        this.genero = genero;
    }

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
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", calificacion=" + calificacion +
                ", listaPersonajes=" + listaPersonajes +
                ", genero=" + genero +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Pelicula)) return false;
        Pelicula pelicula = (Pelicula) o;
        return getId() == pelicula.getId() && getCalificacion() == pelicula.getCalificacion() && Objects.equals(getTitulo(), pelicula.getTitulo()) && Objects.equals(getFechaCreacion(), pelicula.getFechaCreacion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitulo(), getFechaCreacion(), getCalificacion());
    }
}
