package com.besysoft.bootcampspringboot.entidades.manyToOne;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="marcas")
public class Marca {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    @OneToMany(mappedBy = "marca")
    private List<Modelo> listaModelos;

    public Marca() {
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

    public List<Modelo> getListaModelos() {
        return listaModelos;
    }

    public void setListaModelos(List<Modelo> listaModelos) {
        this.listaModelos = listaModelos;
    }
}
