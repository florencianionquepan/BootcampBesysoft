package com.besysoft.ejercitacion.dominio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
@Entity
@Getter
@Setter
@Table(name="peliculas")
public class Pelicula implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 30)
    private String titulo;
    private LocalDate fechaCreacion;
    @Column(length = 1)
    private int calificacion;
    private String imagenURL;
    @ManyToMany(mappedBy = "listaPeliculas", fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JsonIgnoreProperties(value="listaPeliculas")
    private List<Personaje> listaPersonajes;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinColumn(name = "genero_id")
    //@JsonIgnore
    private Genero genero;

    public Pelicula() {
    }

    public Pelicula(int id, String titulo, LocalDate fechaCreacion, int calificacion,
                    String imagenURL, List<Personaje> listaPersonajes) {
        this.id = id;
        this.titulo = titulo;
        this.fechaCreacion = fechaCreacion;
        this.calificacion = calificacion;
        this.imagenURL = imagenURL;
        this.listaPersonajes = listaPersonajes;
    }

    @Override
    public String toString() {
        return "Pelicula{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", fechaCreacion=" + fechaCreacion +
                ", calificacion=" + calificacion +
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
