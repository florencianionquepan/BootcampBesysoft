package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.database.GeneroRepository;
import com.besysoft.ejercitacion.repositorios.database.PeliculaRepository;
import com.besysoft.ejercitacion.repositorios.database.PersonajeRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImp implements IPeliculaService {

    private Logger logger= LoggerFactory.getLogger(PeliculaServiceImp.class);
    private final PeliculaRepository repoPeli;
    private final GeneroRepository repoGen;
    private final PersonajeRepository persoRepo;
    private final IPersonajeService persoService;

    public PeliculaServiceImp(PeliculaRepository repoPeli, GeneroRepository repoGen, PersonajeRepository persoRepo, IPersonajeService persoService) {
        this.repoPeli = repoPeli;
        this.repoGen = repoGen;
        this.persoRepo = persoRepo;
        this.persoService = persoService;
    }


    @Override
    public List<Pelicula> verPelis() {
        return (List<Pelicula>) this.repoPeli.findAll();
    }

    @Override
    public List<Pelicula> buscarPeliByTitulo(String titulo) {
        Optional <Pelicula> oPeli=this.repoPeli.findByTitle(titulo);
        if(oPeli.isEmpty()){
            List<Pelicula> pelis=new ArrayList<>();
        }
        return new ArrayList<Pelicula>(Arrays.asList(oPeli.get()));
    }

    @Override
    public List<Pelicula> buscarPeliByGenero(String genero) {
        List<Pelicula> listaPelis=new ArrayList<Pelicula>();
        Optional <Genero> oGen=this.repoGen.findByName(genero);
        if(oGen.isPresent()){
            listaPelis=oGen.get().getListaPelis();
        }
        return listaPelis;
    }

    @Override
    public List<Pelicula> buscarPeliByFechas(LocalDate desde, LocalDate hasta) {
        return (List<Pelicula>) this.repoPeli.findBetweenDates(desde,hasta);
    }

    @Override
    public List<Pelicula> buscarPeliByCal(int desde, int hasta) {
        return (List<Pelicula>) this.repoPeli.findBetweenCalif(desde,hasta);
    }

    @Override
    public Pelicula altaPeli(Pelicula peli) {
        Pelicula pelicu=this.porSiListaPersoNull(peli);
        if(this.existeTitulo(peli)){
            //"Pelicula existente", 0
            return null;
        }
        if(!this.persoService.sonPersoCorrectos(pelicu.getListaPersonajes())){
            //"Algun personaje ingresado no existe",0
            return null;
        }
        this.addPeliPersos(peli);
        return this.repoPeli.save(peli);
    }

    @Override
    public Pelicula modiPeli(Pelicula peli, int id) {
        Pelicula pelicu=this.porSiListaPersoNull(peli);
        if(!this.existePeli(id)) {
            //"La pelicula con id %d ingresado no existe", id
            return null;
        }
        if(this.existeTituloConOtroId(peli, id)){
            //"Ya existe una pelicula con ese nombre", 0
            return null;
        }
        if(!persoService.sonPersoCorrectos(pelicu.getListaPersonajes())){
            //"Algun personaje ingresado no existe",0
            return null;
        }
        peli.setId(id);
        this.retenerGenero(peli);
        this.removePeliPersos(id);
        this.addPeliPersos(peli);
        return this.repoPeli.save(peli);
    }

    private void addPeliPersos(Pelicula peli){
        //En este caso para que tome bien la relacion la BD debi guardale la pelicula a perso
        //no a persoIn, sino ejecuta los delete de la fc. remove y no hace los nuevos insert.
        for(Personaje perso: peli.getListaPersonajes()){
            Personaje persoIn=this.persoRepo.findById(perso.getId()).get();
            List<Pelicula> listaPelisPersoNuevo=persoIn.getListaPeliculas();
            listaPelisPersoNuevo.add(peli);
            perso.setListaPeliculas(listaPelisPersoNuevo);
            logger.info("Peli agregada a personaje: "+perso);
        }
    }

    private void removePeliPersos(int id){
        Pelicula peliVieja=this.repoPeli.findById(id).get();
        for(Personaje persoAnt: peliVieja.getListaPersonajes()){
            Personaje persoAntBD=this.persoRepo.findById(persoAnt.getId()).get();
            List<Pelicula> listaPelisPerso=persoAntBD.getListaPeliculas();
            listaPelisPerso.remove(peliVieja);
            persoAntBD.setListaPeliculas(listaPelisPerso);
            logger.info("Peli eliminada de personaje: "+persoAntBD);
        }
    }

    public void retenerGenero(Pelicula peli){
        if(peli.getGenero()==null){
            //Sino trae genero en el json seguira con el mismo
            Genero gen=this.repoPeli.findById(peli.getId()).get().getGenero();
            peli.setGenero(gen);
        }
    }
    @Override
    public boolean sonPelisCorrectas(List<Pelicula> pelisIn) {
        boolean sonCorrectas;
        int contadorCorrectas=0;
        for(Pelicula peliIn:pelisIn){
            Optional<Pelicula> oPeliAsociada=this.repoPeli.findById(peliIn.getId());
            if(oPeliAsociada.isEmpty()){
                return false;
            }
            contadorCorrectas=oPeliAsociada.get().equals(peliIn)?contadorCorrectas+1:contadorCorrectas;
        }
        sonCorrectas=contadorCorrectas==pelisIn.size();
        return sonCorrectas;
    }

    //puedo reemplazar con existsById
    @Override
    public boolean existePeli(int id) {
        boolean existe=false;
        Optional <Pelicula> oPeli=this.repoPeli.findById(id);
        if(oPeli.isPresent()){
            existe=true;
        }
        return existe;
    }

    @Override
    public Pelicula porSiListaPersoNull(Pelicula peli) {
        if(peli.getListaPersonajes()==null){
            List<Personaje> listaPerVacia=new ArrayList<>();
            peli.setListaPersonajes(listaPerVacia);
        }
        return peli;
    }

    @Override
    public boolean existeTitulo(Pelicula peli) {
        boolean existe=true;
        Optional <Pelicula> oPeli=this.repoPeli.findByTitle(peli.getTitulo());
        if (oPeli.isEmpty()){
            existe=false;
        }
        return existe;
    }

    @Override
    public boolean existeTituloConOtroId(Pelicula peli, int id) {
        boolean existe=true;
        Optional <Pelicula> oPeli=this.repoPeli.findByTitle(peli.getTitulo());
        Optional <Pelicula> oPeliId=this.repoPeli.findById(id);
        //Si se modifica el nombre del genero por uno que aun no existe
        if (oPeli.isEmpty()){
            existe=false;
        }
        //si el nombre existe, pero se esta tratando del mismo id:
        if(oPeli.isPresent() && oPeli.get().getId()==oPeliId.get().getId()){
            existe=false;
        }
        return existe;
    }
}
