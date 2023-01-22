package com.besysoft.bootcampspringboot.Controller;

import dominio.Persona;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/personas")
public class PersonaController {
    private List<Persona> listaPersonas;

    public PersonaController() {
        this.listaPersonas= new ArrayList<Persona>(
                Arrays.asList(
                        new Persona(1L, "Florencia", "Nonqueapn","361920"),
                        new Persona(2L,"Marcos","Papapaleo","9242500")

                )
        );
    }

    @GetMapping
    public List<Persona> obtenerTodos(){
        //Array.asList reemplaza a todos los .add de ArrayList

        return this.listaPersonas;
    }
    @GetMapping("/{id}")
    public Persona buscarPorId(@PathVariable Long id){
        return this.listaPersonas
                .stream()
                .filter(persona->persona.getId()==id)
                .findFirst()
                .orElse(new Persona());
    }

    public Persona altaPersona(@RequestBody Persona persona){
        //agregar validaciones pragmaticas
        persona.setId((long) (this.listaPersonas.size()+1));
        this.listaPersonas.add(persona);
        return persona;
    }
    @PostMapping
    public ResponseEntity<Persona> newPersona(@RequestBody Persona persona){
        //si quiero mandar info por headers:
        HttpHeaders headers=new HttpHeaders();
        headers.set("app-info","contacto@bootcamp.com");

        persona.setId((long) (this.listaPersonas.size()+1));
        this.listaPersonas.add(persona);
        //por buena practica tiene que darme status code 201!!, y quiero que me devuelva la persona
        return new ResponseEntity<>(persona,headers,HttpStatus.CREATED);
        /*
        return ResponseEntity
        .status.CREATED
        .body(persona);
         */
    }


    public Persona updatePersona(@PathVariable Long id,
                                 @RequestBody Persona persona){
        //Nos encapsula el error nullPointerException
        Optional<Persona> oPersona =this.listaPersonas.stream()
                .filter(p-> p.getId()==id).findAny();
        //No es correcto que nos envie un error 500 ya que no es un error del servidor
        //sino del cliente.Excepto que no exista en la base de datos-
        /*
        if(!oPersona.isPresent()){
            throw new RuntimeException("El id ingresado no existe");
        }
         */
        this.listaPersonas.forEach(p->{
            if(p.getId()==id){
                p.setNombre(persona.getNombre());
                p.setApellido(persona.getApellido());
            }
        });
        return persona;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> upPersona(@PathVariable Long id,
                                 @RequestBody Persona persona){
        Map<String,Object> mensajeBody= new HashMap<>();
        Optional<Persona> oPersona =this.listaPersonas.stream()
                .filter(p-> p.getId()==id).findAny();
        if(!oPersona.isPresent()){
            mensajeBody.put("Success",Boolean.FALSE);
                    mensajeBody.put("data",String.format("El id %d ingresado no existe",id));
            return ResponseEntity
                    .badRequest()
                    .body(mensajeBody);
        }
        this.listaPersonas.forEach(p->{
            if(p.getId()==id){
                p.setNombre(persona.getNombre());
                p.setApellido(persona.getApellido());
            }
        });
        int indice=(int) (id-1);
        mensajeBody.put("Success",Boolean.TRUE);
        mensajeBody.put("data",this.listaPersonas.get(indice));
        return ResponseEntity.ok(mensajeBody);
    }
    @DeleteMapping("/{id}")
    public void deletePersona(@PathVariable Long id){
        int indice=(int) (id-1L);
        this.listaPersonas.remove(indice);
    }
}
