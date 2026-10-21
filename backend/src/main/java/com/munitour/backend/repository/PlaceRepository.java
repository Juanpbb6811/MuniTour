package com.munitour.backend.repository;

import com.munitour.backend.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar lugares turísticos y gastronómicos.
 * Permite filtrar por destino, tipo (cultura/sabor) y municipio.
 */
@Repository
public interface PlaceRepository extends JpaRepository<Place, String> {

    List<Place> findByDestinationId(String destinationId);

    List<Place> findByDestinationIdAndKind(String destinationId, String kind);

    List<Place> findByMunicipality(String municipality);
}
