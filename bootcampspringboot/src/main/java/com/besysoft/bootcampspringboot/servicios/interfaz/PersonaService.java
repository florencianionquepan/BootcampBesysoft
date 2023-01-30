/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.besysoft.bootcampspringboot.servicios.interfaz;

import dominio.Persona;

import java.util.Optional;
public interface PersonaService {
    Persona altaPersona(Persona persona);
    Optional<Persona> buscarPorDni(String dni);
    Iterable<Persona> obtenerTodos();

    
    
}
