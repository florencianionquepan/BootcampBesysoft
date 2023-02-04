package com.besysoft.bootcampspringboot.entidades.manyToOne;

import javax.persistence.*;

@Entity
@Table(name="modelos")
public class Modelo {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @ManyToOne()
    @JoinColumn(name="marca_id")
    private Marca marca;

    public Modelo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
