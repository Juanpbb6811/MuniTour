package com.munitour.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador de bienvenida y estado del servicio para la raíz del backend.
 */
@RestController
public class HomeController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> root() {
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("status", "ONLINE");
        response.put("service", "MuniTour REST API - Sabana Centro");
        response.put("version", "1.0.0");
        response.put("swagger_docs", "/swagger-ui.html");
        response.put("public_endpoints", List.of(
                "/api/places",
                "/api/places/import-osm",
                "/api/dishes",
                "/api/events",
                "/api/routes/inter-municipal",
                "/api/reviews",
                "/api/auth/login",
                "/api/auth/register",
                "/api/auth/google"
        ));
        return ResponseEntity.ok(response);
    }
}
