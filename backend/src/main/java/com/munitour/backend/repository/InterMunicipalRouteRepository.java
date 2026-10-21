package com.munitour.backend.repository;

import com.munitour.backend.entity.InterMunicipalRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio para gestionar rutas intermunicipales.
 * Hereda findAll() de JpaRepository para listar todas las rutas.
 */
@Repository
public interface InterMunicipalRouteRepository extends JpaRepository<InterMunicipalRoute, String> {
    // findAll() se hereda de JpaRepository
}
