package com.munitour.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Clase principal de la aplicación MuniTour.
 * Plataforma de turismo municipal para Colombia.
 * Habilita tareas programadas (@Scheduled) para sincronización automática de lugares y eventos.
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class MunitourBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MunitourBackendApplication.class, args);
    }

}
