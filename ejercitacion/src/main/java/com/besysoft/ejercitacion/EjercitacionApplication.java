package com.besysoft.ejercitacion;

import com.besysoft.ejercitacion.utilidades.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EjercitacionApplication {

	public static void main(String[] args) {
		SpringApplication.run(EjercitacionApplication.class, args);
		Test miTest=new Test();
		miTest.generarDatos();
        }

}
