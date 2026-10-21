package com.munitour.backend.repository;

import com.munitour.backend.entity.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar platos y productos gastronómicos.
 * Permite filtrar por destino, categoría y lugar asociado.
 */
@Repository
public interface DishRepository extends JpaRepository<Dish, String> {

    List<Dish> findByDestinationId(String destinationId);

    List<Dish> findByCategory(String category);

    List<Dish> findByDestinationIdAndCategory(String destinationId, String category);

    List<Dish> findByPlaceId(String placeId);
}
