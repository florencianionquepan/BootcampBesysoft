package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.database.PersonajeRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonajeServiceImp implements IPersonajeService {
    private final PersonajeRepository persoRepo;

    public PersonajeServiceImp(PersonajeRepository persoRepo) {
        this.persoRepo = persoRepo;
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
        return this.persoRepo.save(personaje);
    }
    @Override
    public Personaje modiPersonaje(Personaje perso, int id) {
        perso.setId(id);
        return this.persoRepo.save(perso);
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
            //List<Pelicula> guardoLista = oPerso.get().getListaPeliculas();
            //Personaje aux = oPerso.get();
            //aux.setListaPeliculas(null);
            contCorrectos = oPerso.get().equals(per) ? contCorrectos + 1 : contCorrectos;
            //aux.setListaPeliculas(guardoLista);
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
