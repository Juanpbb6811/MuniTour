package com.munitour.backend.repository;

import com.munitour.backend.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para gestionar usuarios de la aplicación.
 * Soporta búsqueda por email y por Google ID (OAuth).
 */
@Repository
public interface UserRepository extends JpaRepository<AppUser, String> {

    Optional<AppUser> findByEmail(String email);

    Optional<AppUser> findByGoogleId(String googleId);

    boolean existsByEmail(String email);
}
