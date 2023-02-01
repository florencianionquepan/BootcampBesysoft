package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.IPersonajeRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonajeServiceImp implements IPersonajeService {
    private final IPersonajeRepository repository;

    public PersonajeServiceImp(IPersonajeRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Personaje> verPerso() {
        return this.repository.verPerso();
    }

    @Override
    public List<Personaje> buscarPersoByNombre(String nombre) {
        return this.repository.buscarPersoByNombre(nombre);
    }

    @Override
    public List<Personaje> buscarPersoByEdad(int edad) {
        return this.repository.buscarPersoByEdad(edad);
    }

    @Override
    public List<Personaje> buscarPersoRangoEdad(int desde, int hasta) {
        return this.repository.buscarPersoRangoEdad(desde,hasta);
    }

    @Override
    public Personaje altaPersonaje(Personaje personaje) {
        return this.repository.altaPersonaje(personaje);
    }
    @Override
    public Personaje modiPersonaje(Personaje perso, int id) {
        return this.repository.modiPersonaje(perso,id);
    }

    @Override
    public boolean existePerso(int id) {
        boolean existe=false;
        Optional <Personaje> oPerso=this.repository.buscarPersoById(id);
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
            Optional <Personaje> oPerso = this.repository.buscarPersoById(per.getId());
            if (oPerso.isEmpty()) {
                return false;
            }
            //List<Pelicula> guardoLista = oPerso.get().getListaPeliculas();
            Personaje aux = oPerso.get();
            aux.setListaPeliculas(null);
            contCorrectos = aux.equals(per) ? contCorrectos + 1 : contCorrectos;
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
