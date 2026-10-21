package com.munitour.backend.repository;

import com.munitour.backend.entity.CultureRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar rutas culturales dentro de un municipio.
 * Incluye rutas con audio-guía patrimonial.
 */
@Repository
public interface CultureRouteRepository extends JpaRepository<CultureRoute, String> {

    List<CultureRoute> findByDestinationId(String destinationId);
}
