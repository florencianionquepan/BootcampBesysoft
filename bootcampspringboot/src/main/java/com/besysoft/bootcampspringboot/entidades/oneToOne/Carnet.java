package com.besysoft.bootcampspringboot.entidades.oneToOne;

import javax.persistence.*;

@Entity
@Table(name="carnets")
public class Carnet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 10,nullable = false, unique = true)
    private String numero;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="socio_id") //hibernate arma sino este mismo nombre!
    private Socio socio;
    public Carnet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }
}
