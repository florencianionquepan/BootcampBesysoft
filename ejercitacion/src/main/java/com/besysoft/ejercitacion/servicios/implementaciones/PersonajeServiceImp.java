package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.database.PeliculaRepository;
import com.besysoft.ejercitacion.repositorios.database.PersonajeRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonajeServiceImp implements IPersonajeService {
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
        for(Pelicula peli: personaje.getListaPeliculas()){
            this.peliService.retenerGenero(peli);
        }
        this.addPersoPelis(personaje);
        return this.persoRepo.save(personaje);
    }
    @Override
    public Personaje modiPersonaje(Personaje perso, int id) {
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
        }
    }

    private void removePersoPelis(int id){
        Personaje persoViejo=this.persoRepo.findById(id).get();
        for(Pelicula peliAnt:persoViejo.getListaPeliculas()){
            Pelicula peliAntBD=this.peliRepo.findById(peliAnt.getId()).get();
            List<Personaje> listaPersoPeli=peliAntBD.getListaPersonajes();
            listaPersoPeli.remove(persoViejo);
            peliAntBD.setListaPersonajes(listaPersoPeli);
        }
    }

    @Override
    public boolean existePerso(int id) {
        boolean existe=false;
        Optional <Personaje> oPerso=this.persoRepo.findById(id);
        if(oPerso.isPresent()){
            existe=true;
        }
        return existe;
    }

    @Override
    public boolean sonPersoCorrectos(List<Personaje> persosIn) {
        boolean sonCorrectos;
        int contCorrectos = 0;
        for (Personaje per : persosIn) {
            Optional <Personaje> oPerso = this.persoRepo.findById(per.getId());
            if (oPerso.isEmpty()) {
                return false;
            }
            contCorrectos = oPerso.get().equals(per) ? contCorrectos + 1 : contCorrectos;
        }
        sonCorrectos=contCorrectos==persosIn.size();
        return sonCorrectos;
    }

    public Personaje porSiListaPelisNull(Personaje perso){
        if(perso.getListaPeliculas()==null){
            List<Pelicula> listaPelis=new ArrayList<>();
            perso.setListaPeliculas(listaPelis);
        }
        return perso;
    }
}
