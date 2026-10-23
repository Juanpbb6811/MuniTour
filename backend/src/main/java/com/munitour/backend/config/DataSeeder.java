package com.munitour.backend.config;

import com.munitour.backend.service.OsmPlaceImporterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Inicializador de datos al arrancar la aplicación.
 * Verifica si la tabla de lugares contiene datos. Si contiene pocos registros,
 * auto-importa restaurantes reales desde OpenStreetMap (Overpass API) enriquecidos con fotos HD.
 */
@Configuration
@Slf4j
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;
    private final OsmPlaceImporterService osmPlaceImporterService;

    @Override
    public void run(String... args) {
        try {
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM places", Integer.class
            );
            if (count != null && count < 10) {
                log.info("Tabla 'places' contiene pocos registros ({}). Auto-importando restaurantes de Cajicá desde OpenStreetMap...", count);
                new Thread(() -> {
                    try {
                        osmPlaceImporterService.importRestaurantsForMunicipality("cajica", "Cajicá", 4.9186, -74.0259, 5000);
                    } catch (Exception ex) {
                        log.warn("No se pudo auto-importar desde OSM en el arranque: {}", ex.getMessage());
                    }
                }).start();
            } else {
                log.info("✅ La tabla 'places' contiene {} registros.", count);
            }
        } catch (Exception e) {
            log.warn("⚠️  Aviso en DataSeeder: {}", e.getMessage());
        }
    }

}
