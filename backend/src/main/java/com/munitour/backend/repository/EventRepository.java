package com.munitour.backend.repository;

import com.munitour.backend.entity.CulturalEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar eventos culturales programados.
 * Permite filtrar por destino y por día del mes.
 */
@Repository
public interface EventRepository extends JpaRepository<CulturalEvent, String> {

    List<CulturalEvent> findByDestinationId(String destinationId);

    List<CulturalEvent> findByDay(Integer day);

    List<CulturalEvent> findByDestinationIdAndDay(String destinationId, Integer day);
}
