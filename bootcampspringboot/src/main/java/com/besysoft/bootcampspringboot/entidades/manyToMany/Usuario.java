package com.besysoft.bootcampspringboot.entidades.manyToMany;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="user_name")
    private String nombre;
    @ManyToMany()
    @JoinTable(
            name="suscriptores",
            joinColumns = @JoinColumn(name="usuario_id"),
            inverseJoinColumns = @JoinColumn (name="canal_id")
    )
    private List<Canal> listaCanales;

    public Usuario() {
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

    public List<Canal> getListaCanales() {
        return listaCanales;
    }

    public void setListaCanales(List<Canal> listaCanales) {
        this.listaCanales = listaCanales;
    }
}
