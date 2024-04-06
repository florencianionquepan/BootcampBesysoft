package com.besysoft.ejercitacion.dominio;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@Table(name="personajes")
public class Personaje implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(nullable = false, length = 30)
    private String nombre;
    private int edad;
    private double peso;
    private String historia;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
    @JoinTable(
            name="personajes_peliculas",
            joinColumns = @JoinColumn(name="personaje_id"),
            inverseJoinColumns = @JoinColumn (name="peli_id")
    )
    //@JsonIgnoreProperties(value="listaPersonajes")
    private List<Pelicula> listaPeliculas;

    public Personaje() {
    }

    public Personaje(int id, String nombre, int edad, double peso, String historia,
                     List<Pelicula> listaPeliculas) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.historia = historia;
        this.listaPeliculas = listaPeliculas;
    }

    @Override
    public String toString() {
        return "Personaje{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", peso=" + peso +
                ", historia='" + historia + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Personaje)) return false;
        Personaje personaje = (Personaje) o;
        return getId() == personaje.getId() && getEdad() == personaje.getEdad() && Double.compare(personaje.getPeso(), getPeso()) == 0 && Objects.equals(getNombre(), personaje.getNombre()) && Objects.equals(getHistoria(), personaje.getHistoria());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNombre(), getEdad(), getPeso(), getHistoria());
    }
}
