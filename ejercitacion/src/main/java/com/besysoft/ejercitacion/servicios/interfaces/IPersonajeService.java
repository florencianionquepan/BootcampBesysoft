package com.besysoft.ejercitacion.servicios.interfaces;

import com.besysoft.ejercitacion.dominio.Personaje;

import java.util.List;
import java.util.Optional;

public interface IPersonajeService {
    List<Personaje> verPerso();
    List<Personaje> buscarPersoByNombre(String nombre);
    List<Personaje> buscarPersoByEdad(int edad);
    List<Personaje> buscarPersoRangoEdad(int desde, int hasta);
    Personaje altaPersonaje(Personaje perso);
    boolean existePerso(int id);
    boolean sonPersoCorrectos(List<Personaje> persosIn);
    Personaje modiPersonaje(Personaje perso, int id);

}
