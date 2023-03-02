package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.excepciones.ExistException;
import com.besysoft.ejercitacion.excepciones.ListaIncorrectaException;
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

    public PeliculaServiceImp(PeliculaRepository repoPeli, GeneroRepository repoGen, PersonajeRepository persoRepo) {
        this.repoPeli = repoPeli;
        this.repoGen = repoGen;
        this.persoRepo = persoRepo;
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
            throw new ExistException(
                    String.format("La pelicula %s ya existe",pelicu.getTitulo())
            );
        }
        if(!this.sonPersoCorrectos(pelicu.getListaPersonajes())){
            throw new ListaIncorrectaException(
                    "Algun personaje ingresado no existe o no es correcto");
        }
        this.addPeliPersos(peli);
        return this.repoPeli.save(peli);
    }

    @Override
    public Pelicula modiPeli(Pelicula peli, int id) {
        Pelicula pelicu=this.porSiListaPersoNull(peli);
        if(!this.existePeli(id)) {
            throw new ExistException(String.format("La pelicula con id %d no existe",id)
            );
        }
        if(this.existeTituloConOtroId(peli, id)){
            throw new ExistException(
                    String.format("La pelicula %s ya existe",pelicu.getTitulo())
            );
        }
        if(!this.sonPersoCorrectos(pelicu.getListaPersonajes())){
            throw new ListaIncorrectaException(
                    "Algun personaje ingresado no existe o no es correcto");
        }
        peli.setId(id);
        this.retenerGenero(peli);
        this.removePeliPersos(id);
        this.addPeliPersos(peli);
        return this.repoPeli.save(peli);
    }

    protected void addPeliPersos(Pelicula peli){
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

    protected void removePeliPersos(int id){
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

    private boolean existePeli(int id) {
        return this.repoPeli.existsById(id);
    }

    private Pelicula porSiListaPersoNull(Pelicula peli) {
        if(peli.getListaPersonajes()==null){
            List<Personaje> listaPerVacia=new ArrayList<>();
            peli.setListaPersonajes(listaPerVacia);
        }
        return peli;
    }

    private boolean existeTitulo(Pelicula peli) {
        boolean existe=true;
        Optional <Pelicula> oPeli=this.repoPeli.findByTitle(peli.getTitulo());
        if (oPeli.isEmpty()){
            existe=false;
        }
        return existe;
    }

    private boolean existeTituloConOtroId(Pelicula peli, int id) {
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

    private boolean sonPersoCorrectos(List<Personaje> persosIn) {
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
}
