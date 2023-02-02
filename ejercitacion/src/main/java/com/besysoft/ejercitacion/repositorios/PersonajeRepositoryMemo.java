package com.besysoft.ejercitacion.repositorios;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.utilidades.Test;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonajeRepositoryMemo implements IPersonajeRepository{

    private List<Personaje> listaPerso= Test.listaPerso;
    private final IPeliculaRepository peliRepo;

    public PersonajeRepositoryMemo(@Lazy IPeliculaRepository peliRepo) {
        this.peliRepo = peliRepo;
    }

    @Override
    public List<Personaje> verPerso() {
        return this.listaPerso;
    }

    @Override
    public List<Personaje> buscarPersoByNombre(String nombre) {
        List<Personaje> listaPer=this.listaPerso.stream()
                            .filter(personaje -> personaje.getNombre().equals(nombre))
                            .collect(Collectors.toList());
        return listaPer;
    }

    @Override
    public List<Personaje> buscarPersoByEdad(int edad) {
        List<Personaje> listaPer=this.listaPerso.stream()
                            .filter(personaje -> personaje.getEdad()==edad)
                            .collect(Collectors.toList());
        return listaPer;
    }

    @Override
    public List<Personaje> buscarPersoRangoEdad(int desde, int hasta) {
        List<Personaje> listaPer=this.listaPerso.stream()
                            .filter(per -> per.getEdad()<hasta && per.getEdad()>desde)
                            .collect(Collectors.toList());
        return listaPer;
    }

    @Override
    public Personaje altaPersonaje(Personaje perso) {
        perso.setId(this.listaPerso.size()+1);
        this.listaPerso.add(perso);
        this.peliRepo.addPersoAPeli(perso);
        return perso;
    }

    @Override
    public Optional<Personaje> buscarPersoById(int id) {
        Optional<Personaje> oPerso=this.listaPerso
                                .stream()
                                .filter(pel->pel.getId()==id)
                                .findAny();
        return oPerso;
    }

    @Override
    public Personaje modiPersonaje(Personaje perso,int id) {
        listaPerso.forEach(per->{
            if(per.getId()==id){
                per.setNombre(perso.getNombre());
                per.setEdad(perso.getEdad());
                per.setHistoria(perso.getHistoria());
                per.setPeso(perso.getPeso());
                //Primero del lado de las peliculas remuevo el personaje anterior
                this.peliRepo.removePersoDePeli(per);
                //y agrego el personaje actual en las que trae. Para eso debo setearle el id.
                perso.setId(id);
                this.peliRepo.addPersoAPeli(perso);
                per.setListaPeliculas(perso.getListaPeliculas());
            }
                });
        return perso;
    }

    @Override
    public void addPeliAPerso(Pelicula peliNueva) {
        for(Personaje per: peliNueva.getListaPersonajes()){
            Personaje persoData=this.listaPerso.stream().filter(p->p.getId()==per.getId())
                    .findAny().get();
            persoData.getListaPeliculas().add(peliNueva);
        }
    }

    @Override
    public void removePeliDePerson(Pelicula peliVieja) {
        for(Personaje per: peliVieja.getListaPersonajes()) {
            Personaje persoData = this.listaPerso.stream().filter(p -> p.getId() == per.getId())
                    .findAny().get();
            persoData.getListaPeliculas().remove(peliVieja);
        }
    }


}
