package com.besysoft.ejercitacion.servicios.implementaciones;

import com.besysoft.ejercitacion.dominio.Pelicula;
import com.besysoft.ejercitacion.dominio.Personaje;
import com.besysoft.ejercitacion.repositorios.IPeliculaRepository;
import com.besysoft.ejercitacion.servicios.interfaces.IPeliculaService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PeliculaServiceImp implements IPeliculaService {
    private final IPeliculaRepository repoPeli;

    public PeliculaServiceImp(IPeliculaRepository repoPeli) {
        this.repoPeli = repoPeli;
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
        return this.repoPeli.altaPeli(peli);
    }

    @Override
    public Pelicula modiPeli(Pelicula peli, int id) {
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
            List<Personaje> guardoPerso=oPeliAsociada.get().getListaPersonajes();
            Pelicula aux = oPeliAsociada.get();
            aux.setListaPersonajes(null);
            contadorCorrectas=aux.equals(peliIn)?contadorCorrectas+1:contadorCorrectas;
            aux.setListaPersonajes(guardoPerso);
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

    @Override
    public Pelicula porSiListaPersoNull(Pelicula peli) {
        if(peli.getListaPersonajes()==null){
            List<Personaje> listaPerVacia=new ArrayList<>();
            peli.setListaPersonajes(listaPerVacia);
        }
        return peli;
    }
}
