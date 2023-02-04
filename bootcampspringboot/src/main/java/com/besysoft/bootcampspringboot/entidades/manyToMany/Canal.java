package com.besysoft.bootcampspringboot.entidades.manyToMany;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="canales")
public class Canal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="nombre_canal")
    private String nombre;

    @ManyToMany(mappedBy = "listaCanales", fetch = FetchType.LAZY)
    private List<Usuario> listaUsuarios;

    public Canal() {
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

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }
}
