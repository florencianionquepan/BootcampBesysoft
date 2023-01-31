package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.IPeliculaRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import com.besysoft.ejercitacion.servicios.interfaces.IPersonajeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImp implements IPeliculaService {
    private final IPeliculaRepository repoPeli;
    private final PersonajeServiceImp persoSer;

    public PeliculaServiceImp(IPeliculaRepository repoPeli, PersonajeServiceImp personServi) {
        this.repoPeli = repoPeli;
        this.persoSer=personServi;
    }

    @Override
    public List<Pelicula> verPelis() {
        return this.repoPeli.verPelis();
    }

    @Override
    public List<Pelicula> buscarPeliByTitulo(String titulo) {
        return this.repoPeli.buscarPeliByTitulo(titulo);
    }

    @Override
    public List<Pelicula> buscarPeliByGenero(String genero) {
        return this.repoPeli.buscarPeliByGenero(genero);
    }

    @Override
    public List<Pelicula> buscarPeliByFechas(LocalDate desde, LocalDate hasta) {
        return this.repoPeli.buscarPeliByFechas(desde,hasta);
    }

    @Override
    public List<Pelicula> buscarPeliByCal(int desde, int hasta) {
        return this.repoPeli.buscarPeliByCal(desde,hasta);
    }

    @Override
    public Pelicula altaPeli(Pelicula peli) {
        this.porSilistaPersoNull(peli);
        if(!persoSer.sonPersoCorrectos(peli.getListaPersonajes())){
            return null;
        }
        //ControladoraPersonaje.addPeliPerso(peli);
        return this.repoPeli.altaPeli(peli);
    }

    @Override
    public Pelicula modiPeli(Pelicula peli, int id) {
        this.porSilistaPersoNull(peli);
        if(!this.existePeli(id)){
            return null;
        }
        if(!persoSer.sonPersoCorrectos(peli.getListaPersonajes())){
            return null;
        }
        return this.repoPeli.modiPeli(peli,id);
    }

    @Override
    public boolean sonPelisCorrectas(List<Pelicula> pelisIn) {
        boolean sonCorrectas;
        int contadorCorrectas=0;
        for(Pelicula peliIn:pelisIn){
            Optional<Pelicula> oPeliAsociada=this.repoPeli.buscarPeliById(peliIn.getId());
            if(oPeliAsociada.isEmpty()){
                return false;
            }
            //Luego ver si al equals le puedo borrar la lista de personajes si no lo voy a necesitar en otro lado
            //List<Personaje> guardoPerso=oPeliAsociada.get().getListaPersonajes(); CREO QUE AHORA ESTO NO LO NECESITO
            Pelicula aux = oPeliAsociada.get();
            aux.setListaPersonajes(null);
            contadorCorrectas=aux.equals(peliIn)?contadorCorrectas+1:contadorCorrectas;
            //aux.setListaPersonajes(guardoPerso);CREO QUE AHORA ESTO NO LO NECESITO
        }
        sonCorrectas=contadorCorrectas==pelisIn.size();
        return sonCorrectas;
    }

    @Override
    public boolean existePeli(int id) {
        boolean existe=false;
        Optional <Pelicula> oPeli=this.repoPeli.buscarPeliById(id);
        if(oPeli.isPresent()){
            existe=true;
        }
        return existe;
    }

    private void porSilistaPersoNull(Pelicula peli){
        if(peli.getListaPersonajes()==null){
            List<Personaje> listaPerVacia=new ArrayList<>();
            peli.setListaPersonajes(listaPerVacia);
        }
    }
}
