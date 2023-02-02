package com.besysoft.ejercitacion.repositorios;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class GeneroRepositoryMemo implements IGeneroRepository{
    private List<Genero> listaGeneros;

    public GeneroRepositoryMemo(){
        this.listaGeneros=new ArrayList<Genero>(
                Arrays.asList(
                        new Genero(1,"Infantil",new ArrayList<Pelicula>())
                )
        );
    }
    @Override
    public List<Genero> verGeneros() {
        return this.listaGeneros;
    }

    @Override
    public Genero altaGen(Genero gen) {
        gen.setId(listaGeneros.size()+1);
        this.listaGeneros.add(gen);
        return gen;
    }

    @Override
    public Genero modiGen(Genero gen,int id) {
        Genero genero=gen;
        genero.setId(id);
        this.listaGeneros.forEach(g->{
            if(g.getId()==gen.getId()){
                g.setNombre(gen.getNombre());
                g.setListaPelis(gen.getListaPelis());
            }
        });
        return genero;
    }

    @Override
    public Optional<Genero> buscarGeneroById(int id) {
        Optional<Genero> oGen=this.listaGeneros.stream()
                .filter(g->g.getId()==id).findAny();
        return oGen;
    }

    @Override
    public Optional<Genero> buscarGeneroByNombre(String nombre) {
        Optional<Genero> oGen=this.listaGeneros.stream()
                .filter(g->g.getNombre().equalsIgnoreCase(nombre)).findAny();
        return oGen;
    }
}
