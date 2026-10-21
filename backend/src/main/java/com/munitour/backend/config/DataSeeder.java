package com.munitour.backend.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Inicializador de datos al arrancar la aplicación.
 * Verifica si la tabla de lugares está vacía y sugiere ejecutar data.sql
 * para poblar los datos iniciales de turismo.
 */
@Configuration
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public DataSeeder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM places", Integer.class
            );
            if (count != null && count == 0) {
                log.info("⚠️  La tabla 'places' está vacía. Ejecute data.sql para poblar los datos iniciales de turismo.");
            } else {
                log.info("✅ La tabla 'places' contiene {} registros.", count);
            }
        } catch (Exception e) {
            log.warn("⚠️  No se pudo verificar la tabla 'places': {}. Se creará automáticamente con JPA.", e.getMessage());
        }
    }

}
