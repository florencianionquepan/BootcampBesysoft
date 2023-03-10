package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.excepciones.ExistException;
import com.besysoft.ejercitacion.excepciones.ListaIncorrectaException;
import com.besysoft.ejercitacion.repositorios.database.GeneroRepository;
import com.besysoft.ejercitacion.repositorios.database.PeliculaRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IGeneroService;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GeneroServiceImp implements IGeneroService {

    private Logger logger= LoggerFactory.getLogger(GeneroServiceImp.class);
    private final GeneroRepository genRepo;
    private final PeliculaRepository peliRepo;
    private final IPeliculaService peliService;

    public GeneroServiceImp(GeneroRepository genRepo, PeliculaRepository peliRepo, IPeliculaService peliService) {
        this.genRepo = genRepo;
        this.peliRepo = peliRepo;
        this.peliService = peliService;
    }

    @Override
    public List<Genero> verGeneros() {
        return (List<Genero>) this.genRepo.findAll();
    }

    @Override
    public Genero altaGenero(Genero genero)  {
        this.porSiListaPelisNull(genero);
        Optional <Genero> oGen=this.genRepo.findByName(genero.getNombre());
        if(oGen.isPresent()){
            throw new ExistException(
                    String.format("El Genero %s ya existe",genero.getNombre())
            );
        }
        if(!this.peliService.sonPelisCorrectas(genero.getListaPelis())){
            throw new ListaIncorrectaException(
                    "Alguna pelicula ingresada no existe o no es correcta");
        }
        this.addPeli(genero);
        return this.genRepo.save(genero);
    }

    @Override
    public Genero modiGenero(Genero genero, int id) {
        this.porSiListaPelisNull(genero);
        if(!this.existeGenero(id)) {
            throw new ExistException(String.format("El Genero con id %d no existe",id)
            );
        }
        if(this.existeNombreConOtroId(genero,id)){
            throw new ExistException(String.format("El Genero %s ya existe",genero.getNombre())
            );
        }
        if(!this.peliService.sonPelisCorrectas(genero.getListaPelis())){
            throw new ListaIncorrectaException("Alguna pelicula ingresada no existe o no es correcta");
        }
        genero.setId(id);
        //SI O SI AL MODIFICAR GENERO DEBE TRAER SU LISTA DE PELICULAS O SE BORRARAN
        this.removePeli(id);
        this.addPeli(genero);
        return this.genRepo.save(genero);
    }

    private void addPeli(Genero genero){
        List<Pelicula> listaPelis=genero.getListaPelis();
        for(Pelicula peli: listaPelis){
            Pelicula pelGen=peliRepo.findById(peli.getId()).get();
            pelGen.setGenero(genero);
            logger.info("Genero asociado/agregado a pelicula: "+pelGen);
        }
    }

    //las peliculas que no traiga en la lista dejaran de estar asociadas al genero
    private void removePeli(int id){
            Genero genAnte=this.genRepo.findById(id).get();
            List<Pelicula> listaAnte=genAnte.getListaPelis();
            for(Pelicula peliAnte: listaAnte){
                Pelicula pelGen=peliRepo.findById(peliAnte.getId()).get();
                pelGen.setGenero(null);
                logger.info("Genero removido de pelicula: "+pelGen);
            }
    }

    private void porSiListaPelisNull(Genero genero){
        if(genero.getListaPelis()==null){
            List<Pelicula> listaPelis=new ArrayList<>();
            genero.setListaPelis(listaPelis);
        }
    }

    private boolean existeGenero(int id){
        return this.genRepo.existsById(id);
    }

    protected boolean existeNombreConOtroId(Genero genero, int id) {
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
