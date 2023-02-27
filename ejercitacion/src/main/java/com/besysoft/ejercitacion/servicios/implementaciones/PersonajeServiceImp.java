package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.database.PeliculaRepository;
import com.besysoft.ejercitacion.repositorios.database.PersonajeRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonajeServiceImp implements IPersonajeService {

    private Logger logger= LoggerFactory.getLogger(PersonajeServiceImp.class);
    private final PersonajeRepository persoRepo;
    private final IPeliculaService peliService;
    private final PeliculaRepository peliRepo;

    public PersonajeServiceImp(PersonajeRepository persoRepo, IPeliculaService peliService, PeliculaRepository peliRepo) {
        this.persoRepo = persoRepo;
        this.peliService = peliService;
        this.peliRepo = peliRepo;
    }


    @Override
    public List<Personaje> verPerso() {
        return (List<Personaje>) this.persoRepo.findAll();
    }

    @Override
    public List<Personaje> buscarPersoByNombre(String nombre) {
        return this.persoRepo.findByName(nombre);
    }

    @Override
    public List<Personaje> buscarPersoByEdad(int edad) {
        return this.persoRepo.findByAge(edad);
    }

    @Override
    public List<Personaje> buscarPersoRangoEdad(int desde, int hasta) {
        return this.persoRepo.findBetweenAges(desde,hasta);
    }

    @Override
    public Personaje altaPersonaje(Personaje personaje) {
        Personaje person=this.porSiListaPelisNull(personaje);
        if(!this.peliService.sonPelisCorrectas(person.getListaPeliculas())){
            //"Alguna pelicula asociada no existe"
            return null;
        }
        for(Pelicula peli: personaje.getListaPeliculas()){
            this.peliService.retenerGenero(peli);
        }
        this.addPersoPelis(personaje);
        return this.persoRepo.save(personaje);
    }

    @Override
    public Personaje modiPersonaje(Personaje perso, int id) {
        Personaje person=this.porSiListaPelisNull(perso);
        if(!this.existePerso(id)) {
            //"El personaje con id %d ingresado no existe", id);
            return null;
        }
        if(!this.peliService.sonPelisCorrectas(person.getListaPeliculas())) {
            //"Alguna pelicula asociada no existe",0);
            return null;
        }
        perso.setId(id);
        for(Pelicula peli: perso.getListaPeliculas()){
            this.peliService.retenerGenero(peli);
        }
        this.removePersoPelis(id);
        this.addPersoPelis(perso);
        return this.persoRepo.save(perso);
    }

    private void addPersoPelis(Personaje perso){
        for(Pelicula peliNueva:perso.getListaPeliculas()){
            Pelicula peliIn=this.peliRepo.findById(peliNueva.getId()).get();
            List<Personaje> listaPersoPeliNueva=peliIn.getListaPersonajes();
            listaPersoPeliNueva.add(perso);
            peliIn.setListaPersonajes(listaPersoPeliNueva);
            logger.info("Se añadio el personaje a la pelicula "+peliIn);
        }
    }

    private void removePersoPelis(int id){
        Personaje persoViejo=this.persoRepo.findById(id).get();
        for(Pelicula peliAnt:persoViejo.getListaPeliculas()){
            Pelicula peliAntBD=this.peliRepo.findById(peliAnt.getId()).get();
            List<Personaje> listaPersoPeli=peliAntBD.getListaPersonajes();
            listaPersoPeli.remove(persoViejo);
            peliAntBD.setListaPersonajes(listaPersoPeli);
            logger.info("Se removio el personaje de la pelicula "+peliAntBD);
        }
    }

    private boolean existePerso(int id) {
        boolean existe=false;
        Optional <Personaje> oPerso=this.persoRepo.findById(id);
        if(oPerso.isPresent()){
            existe=true;
        }
        return existe;
    }

    private Personaje porSiListaPelisNull(Personaje perso){
        if(perso.getListaPeliculas()==null){
            List<Pelicula> listaPelis=new ArrayList<>();
            perso.setListaPeliculas(listaPelis);
        }
        return perso;
    }
}
