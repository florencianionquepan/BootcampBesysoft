package com.besysoft.bootcampspringboot.config;

import com.besysoft.bootcampspringboot.Servicios.implementacion.PersonaServiceImple;
import com.besysoft.bootcampspringboot.Servicios.interfaz.PersonaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanAppConfig {
    @Bean
    public PersonaService getComponentPersonaService(){
        return new PersonaServiceImple("personaRepositoryMemory");
    }
    //Si lo hago de esta forma no agrego la annotation Service en PersonaServiceImple
}
