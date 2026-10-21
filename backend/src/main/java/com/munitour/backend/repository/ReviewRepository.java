package com.munitour.backend.repository;

import com.munitour.backend.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para gestionar reseñas de lugares.
 * Permite consultar todas las reseñas asociadas a un lugar específico.
 */
@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findByPlaceId(String placeId);
}
