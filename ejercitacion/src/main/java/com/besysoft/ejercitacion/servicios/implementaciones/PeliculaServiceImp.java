package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Genero;
import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.database.GeneroRepository;
import com.besysoft.ejercitacion.repositorios.database.PeliculaRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImp implements IPeliculaService {
    private final PeliculaRepository repoPeli;
    private final GeneroRepository repoGen;

    public PeliculaServiceImp(PeliculaRepository repoPeli, GeneroRepository repoGen) {
        this.repoPeli = repoPeli;
        this.repoGen = repoGen;
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
        return this.repoPeli.save(peli);
    }

    @Override
    public Pelicula modiPeli(Pelicula peli, int id) {
        peli.setId(id);
        this.retenerGenero(peli);
        return this.repoPeli.save(peli);
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
            //List<Personaje> guardoPerso=oPeliAsociada.get().getListaPersonajes();
            //Pelicula aux = oPeliAsociada.get();
            //aux.setListaPersonajes(null);
            contadorCorrectas=oPeliAsociada.get().equals(peliIn)?contadorCorrectas+1:contadorCorrectas;
            //aux.setListaPersonajes(guardoPerso);
        }
        sonCorrectas=contadorCorrectas==pelisIn.size();
        return sonCorrectas;
    }

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
