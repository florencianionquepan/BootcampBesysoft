package com.besysoft.bootcampspringboot.entidades.oneToOne;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="socios")
public class Socio implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, length = 50)
    private String nombre;

    public Socio() {
    }

    public Socio(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
}
