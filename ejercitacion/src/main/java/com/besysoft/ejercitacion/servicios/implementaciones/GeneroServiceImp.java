package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.repositorios.IGeneroRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IGeneroService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImp implements IGeneroService {

    private final IGeneroRepository genRepo;

    public GeneroServiceImp(IGeneroRepository genRepo) {
        this.genRepo = genRepo;
    }

    @Override
    public List<Genero> verGeneros() {
        return this.genRepo.verGeneros();
    }

    @Override
    public Genero altaGenero(Genero genero) {
        return this.genRepo.altaGen(genero);
    }

    @Override
    public Genero modiGenero(Genero genero, int id) {
        return this.genRepo.modiGen(genero,id);
    }

    public void porSiListaPelisNull(Genero genero){
        if(genero.getListaPelis()==null){
            List<Pelicula> listaPelis=new ArrayList<>();
            genero.setListaPelis(listaPelis);
        }
    }

    public boolean existeGenero(int id){
        boolean existe=false;
        Optional<Genero> gen=this.genRepo.buscarGeneroById(id);
        if(gen.isPresent()){
            existe=true;
        }
        return existe;
    }

    @Override
    public boolean existeNombre(Genero genero) {
        boolean existe=true;
        Optional <Genero> oGen=this.genRepo.buscarGeneroByNombre(genero.getNombre());
        if (oGen.isEmpty()){
            existe=false;
        }
        return existe;
    }

    @Override
    public boolean existeNombreConOtroId(Genero genero, int id) {
        boolean existe=true;
        Optional <Genero> oGen=this.genRepo.buscarGeneroByNombre(genero.getNombre());
        Optional<Genero> gen=this.genRepo.buscarGeneroById(id);
        //Si se modifica el nombre del genero por uno que aun no existe
        if (oGen.isEmpty()){
            existe=false;
        }
        //si el nombre existe, pero se esta tratando del mismo id:
        if(oGen.isPresent() && oGen.get().getId()==gen.get().getId()){
            existe=false;
        }
        return existe;
    }
}
