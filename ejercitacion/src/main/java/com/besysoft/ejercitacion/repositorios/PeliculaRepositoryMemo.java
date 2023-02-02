package com.besysoft.ejercitacion.repositorios;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.utilidades.Test;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PeliculaRepositoryMemo implements IPeliculaRepository{
    private List<Pelicula> listaPelis= Test.listaPelis;
    private final IPersonajeRepository persoRepo;
    private final IGeneroRepository genRepo;

    public PeliculaRepositoryMemo(IPersonajeRepository persoRepo, IGeneroRepository genRepo) {
        this.persoRepo = persoRepo;
        this.genRepo = genRepo;
    }

    @Override
    public List<Pelicula> verPelis() {
        return this.listaPelis;
    }

    @Override
    public List<Pelicula> buscarPeliByTitulo(String titulo) {
        List<Pelicula> listaPel=this.listaPelis.stream()
                .filter(pelicula -> pelicula.getTitulo().equals(titulo))
                .collect(Collectors.toList());
        return listaPel;
    }

    @Override
    public List<Pelicula> buscarPeliByGenero(String genero) {
        List<List<Pelicula>> listaPelis= this.genRepo.verGeneros().stream()
                            .filter(gen->gen.getNombre().equals(genero))
                            .map(Genero::getListaPelis)
                            .collect(Collectors.toList());
        List<Pelicula> listaPel=(listaPelis.size()>0)?listaPelis.get(0):new ArrayList<Pelicula>();
        return listaPel;
    }

    @Override
    public List<Pelicula> buscarPeliByFechas(LocalDate desde, LocalDate hasta) {
        List<Pelicula> listaPel = this.listaPelis.stream()
                .filter(pelicula -> pelicula.getFechaCreacion().isAfter(desde)
                        && pelicula.getFechaCreacion().isBefore(hasta))
                .collect(Collectors.toList());
        return listaPel;
    }

    @Override
    public List<Pelicula> buscarPeliByCal(int desde, int hasta) {
        List<Pelicula> listaPel=this.listaPelis.stream()
                .filter(pelicula -> pelicula.getCalificacion()<=hasta
                        && pelicula.getCalificacion()>=desde)
                .collect(Collectors.toList());
        return listaPel;
    }

    @Override
    public Pelicula altaPeli(Pelicula peli) {
        peli.setId(listaPelis.size()+1);
        listaPelis.add(peli);
        //agrego la pelicula a los personajes que trae
        this.persoRepo.addPeliAPerso(peli);
        return peli;
    }

    @Override
    public Pelicula modiPeli(Pelicula peli, int id) {
        this.listaPelis.forEach(pel-> {
            if (pel.getId() == id) {
                pel.setTitulo(peli.getTitulo());
                pel.setFechaCreacion(peli.getFechaCreacion());
                pel.setCalificacion(peli.getCalificacion());
                //Primero del lado de los personajes remuevo la peli vieja pel
                this.persoRepo.removePeliDePerson(pel);
                //y agrego la pelicula actualizada a los personajes que trae
                peli.setId(id);
                this.persoRepo.addPeliAPerso(peli);
                pel.setListaPersonajes(peli.getListaPersonajes());
            }
        });
        return peli;
    }

    @Override
    public Optional <Pelicula> buscarPeliById(int id) {
        Optional<Pelicula> oPeli=this.listaPelis
                .stream()
                .filter(pel->pel.getId()==id)
                .findAny();
        return oPeli;
    }

    public void addPersoAPeli(Personaje persoNuevo){
        for(Pelicula peli: persoNuevo.getListaPeliculas()){
            Pelicula peliData=listaPelis.stream()
                                .filter(pe->pe.getId()==peli.getId())
                                .findAny().get();
            peliData.getListaPersonajes().add(persoNuevo);
        }
    }

    public void removePersoDePeli(Personaje persoViejo){
        for(Pelicula peli:persoViejo.getListaPeliculas()){
            Pelicula peliData=listaPelis.stream()
                    .filter(pe->pe.getId()==peli.getId())
                    .findAny().get();
            peliData.getListaPersonajes().remove(persoViejo);
        }
    }
}
