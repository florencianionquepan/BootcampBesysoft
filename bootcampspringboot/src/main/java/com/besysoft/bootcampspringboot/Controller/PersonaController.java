package com.besysoft.bootcampspringboot.Controller;

import com.besysoft.bootcampspringboot.servicios.interfaz.PersonaService;
import com.besysoft.bootcampspringboot.dominio.Persona;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.*;

@RestController
@RequestMapping("/personas")
public class PersonaController {

    //@Autowired
    //@Qualifier("personaServiceImple") //lo comento porque use @ConditionalOnProperty
    //private PersonaService service;
    //usar la interfaz aca no ninguna clase de la implementacion


    /*
    NO ES BUENA PRACTICA ESTO!!!
    @Autowired
    private PersonaServiceImple personaServiceImple;
    */

    /*  OTRA FORMA DE HACER INYECCION DE DEP. POR CONSTRUCTOR
   */
    private final PersonaService service;

    public PersonaController(PersonaService service){
        this.service=service;
    }

    @GetMapping
    public List<Persona> obtenerTodos() {
        //Array.asList reemplaza a todos los .add de ArrayList

        return (List<Persona>)this.service.obtenerTodos();
    }
/*
    @GetMapping("/{id}")
    public Persona buscarPorId(@PathVariable Long id) {
        return ((List<Persona>)this.service.obtenerTodos())
                .stream()
                .filter(persona -> persona.getId() == id)
                .findFirst()
                .orElse(new Persona());
        //Sino existe una persona con ese id, me va a dar un objeto vacio por el orElse
    }

    public Persona altaPersona(@RequestBody Persona persona) {
        //agregar validaciones pragmaticas
        persona.setId((long) (this.listaPersonas.size() + 1));
        this.listaPersonas.add(persona);
        return persona;
    }
 */

    @PostMapping
    public ResponseEntity<?> newPersona(@RequestBody Persona persona) {
        //si quiero mandar info por headers:
        HttpHeaders headers = new HttpHeaders();
        headers.set("app-info", "contacto@bootcamp.com");
/*
        if(this.dniExiste(persona.getDni())){
            return ResponseEntity.badRequest().body("Dni ya existe");
        }
        persona.setId((long) (this.listaPersonas.size() + 1));
        this.listaPersonas.add(persona);
        */
        Persona personaAdd=this.service.altaPersona(persona);
        //Si personaAdd es nulo deberia manejarlo
        return new ResponseEntity<Persona>(personaAdd, headers, HttpStatus.CREATED);
        //por buena practica tiene que darme status code 201!!, y quiero que me devuelva la persona
        /*
        return ResponseEntity
        .status.CREATED
        .body(persona);
        */
    }

    /*ESTA EL ACTUAL DEBAJO
    public Persona updatePersona(@PathVariable Long id,
                                 @RequestBody Persona persona) {

        //Nos encapsula el error nullPointerException
        Optional<Persona> oPersona = this.listaPersonas.stream()
                .filter(p -> p.getId() == id).findAny();
        //No es correcto que nos envie un error 500 ya que no es un error del servidor
        //sino del cliente.Excepto que no exista en la base de datos-

        if(!oPersona.isPresent()){
            throw new RuntimeException("El id ingresado no existe");
        }

        this.listaPersonas.forEach(p -> {
            if (p.getId() == id) {
                p.setNombre(persona.getNombre());
                p.setApellido(persona.getApellido());
            }
        });
        return persona;
        }
        */


    @PutMapping("/{id}")
    public ResponseEntity<?> upPersona(@PathVariable Long id,
                                       @RequestBody Persona persona) {
        Map<String, Object> mensajeBody = new HashMap<>();
        /*Optional<Persona> oPersona = this.listaPersonas.stream()
                .filter(p -> p.getId() == id).findAny();
        if (!oPersona.isPresent()) {
            mensajeBody.put("Success", Boolean.FALSE);
            mensajeBody.put("data", String.format("El id %d ingresado no existe", id)); //para darle formato al string que voy a armar
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        this.listaPersonas.forEach(p -> {
            if (p.getId() == id) {
                p.setNombre(persona.getNombre());
                p.setApellido(persona.getApellido());
            }
        });
        int indice = (int) (id - 1);
        mensajeBody.put("Success", Boolean.TRUE);
        mensajeBody.put("data", this.listaPersonas.get(indice));*/
        return ResponseEntity.ok(mensajeBody);
    }


    @DeleteMapping("/{id}")
    @ApiIgnore
    public void deletePersona(@PathVariable Long id) {
        int indice = (int) (id - 1L);
        //this.listaPersonas.remove(indice);
    }


/*
    private boolean dniExiste(String dni){
        Optional <Persona> persona=this.listaPersonas
                .stream().filter(per->per.getDni()==dni.trim()).findAny();
        return persona.isPresent();
    }
*/



}
