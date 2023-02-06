package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.repositorios.database.GeneroRepository;
import com.besysoft.ejercitacion.repositorios.database.PeliculaRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IGeneroService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImp implements IGeneroService {

    private final GeneroRepository genRepo;
    private final PeliculaRepository peliRepo;

    public GeneroServiceImp(GeneroRepository genRepo, PeliculaRepository peliRepo) {
        this.genRepo = genRepo;
        this.peliRepo = peliRepo;
    }

    @Override
    public List<Genero> verGeneros() {
        return (List<Genero>) this.genRepo.findAll();
    }

    @Override
    public Genero altaGenero(Genero genero) {
        this.addPeli(genero);
        return this.genRepo.save(genero);
    }

    @Override
    public Genero modiGenero(Genero genero, int id) {
        genero.setId(id);
        if(genero.getListaPelis()==null){
            //Sino trae pelis en el json seguira con las mismas
            List<Pelicula> listaPelis=this.genRepo.findById(genero.getId()).get().getListaPelis();
            genero.setListaPelis(listaPelis);
        }
        this.removePeli(id);
        this.addPeli(genero);
        return this.genRepo.save(genero);
    }

    private void addPeli(Genero genero){
        List<Pelicula> listaPelis=genero.getListaPelis();
        for(Pelicula peli: listaPelis){
            Pelicula pelGen=peliRepo.findById(peli.getId()).get();
            pelGen.setGenero(genero);
        }
    }

    //las peliculas que no traiga en la lista dejaran de estar asociadas al genero
    private void removePeli(int id){
            Genero genAnte=this.genRepo.findById(id).get();
            List<Pelicula> listaAnte=genAnte.getListaPelis();
            for(Pelicula peliAnte: listaAnte){
                Pelicula pelGen=peliRepo.findById(peliAnte.getId()).get();
                pelGen.setGenero(null);
            }
    }

    public void porSiListaPelisNull(Genero genero){
        if(genero.getListaPelis()==null){
            List<Pelicula> listaPelis=new ArrayList<>();
            genero.setListaPelis(listaPelis);
        }
    }

    public boolean existeGenero(int id){
        boolean existe=false;
        Optional<Genero> gen=this.genRepo.findById(id);
        if(gen.isPresent()){
            existe=true;
        }
        return existe;
    }

    public boolean esGeneroCorrecto(Genero genero){
        if(genero!=null){
            boolean ok=false;
            if(this.existeGenero(genero.getId())){
                ok=this.genRepo.findById(genero.getId()).get().equals(genero);
            }
            return ok;
        }
        return true;
    }

    @Override
    public boolean existeNombre(Genero genero) {
        boolean existe=true;
        Optional <Genero> oGen=this.genRepo.findByName(genero.getNombre());
        if (oGen.isEmpty()){
            existe=false;
        }
        return existe;
    }

    @Override
    public boolean existeNombreConOtroId(Genero genero, int id) {
        boolean existe=true;
        Optional <Genero> oGen=this.genRepo.findByName(genero.getNombre());
        Optional<Genero> gen=this.genRepo.findById(id);
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
