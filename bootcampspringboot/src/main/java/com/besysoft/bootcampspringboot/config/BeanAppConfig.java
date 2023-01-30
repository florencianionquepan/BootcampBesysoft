package com.besysoft.bootcampspringboot.config;

import com.besysoft.bootcampspringboot.servicios.implementacion.PersonaServiceImple;
import com.besysoft.bootcampspringboot.servicios.interfaz.PersonaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class BeanAppConfig {
/*    @Bean("nombreBean")
    public PersonaService getComponentPersonaService(){
        return new PersonaServiceImple();
    }*/
    //Si lo hago de esta forma no agrego la annotation Service en PersonaServiceImple
}
