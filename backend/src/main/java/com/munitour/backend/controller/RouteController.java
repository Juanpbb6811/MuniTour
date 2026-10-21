package com.munitour.backend.controller;

import com.munitour.backend.dto.response.InterMunicipalRouteResponseDto;
import com.munitour.backend.dto.response.RouteResponseDto;
import com.munitour.backend.entity.CultureRoute;
import com.munitour.backend.entity.InterMunicipalRoute;
import com.munitour.backend.mapper.InterMunicipalRouteMapper;
import com.munitour.backend.mapper.RouteMapper;
import com.munitour.backend.service.InterMunicipalRouteService;
import com.munitour.backend.service.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/routes", "/api/v1/routes"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Rutas Turísticas", description = "Circuitos culturales con audioguías y rutas intermunicipales")
public class RouteController {

    private final RouteService routeService;
    private final InterMunicipalRouteService interMunicipalRouteService;

    @GetMapping
    @Operation(summary = "Listar rutas culturales con audioguías")
    public ResponseEntity<List<RouteResponseDto>> getRoutes(
            @RequestParam(required = false) String destinationId
    ) {
        List<CultureRoute> routes = routeService.getByDestinationId(destinationId);
        return ResponseEntity.ok(RouteMapper.toResponseDtoList(routes));
    }

    @GetMapping("/inter-municipal")
    @Operation(summary = "Listar rutas turísticas intermunicipales (tren, senderos, cicloturismo)")
    public ResponseEntity<List<InterMunicipalRouteResponseDto>> getInterMunicipalRoutes() {
        List<InterMunicipalRoute> routes = interMunicipalRouteService.getAll();
        return ResponseEntity.ok(InterMunicipalRouteMapper.toResponseDtoList(routes));
    }

    @GetMapping("/inter-municipal/{id}")
    @Operation(summary = "Obtener detalle de una ruta intermunicipal")
    public ResponseEntity<InterMunicipalRouteResponseDto> getInterMunicipalRouteById(@PathVariable String id) {
        InterMunicipalRoute route = interMunicipalRouteService.getById(id);
        return ResponseEntity.ok(InterMunicipalRouteMapper.toResponseDto(route));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de una ruta cultural")
    public ResponseEntity<RouteResponseDto> getRouteById(@PathVariable String id) {
        CultureRoute route = routeService.getById(id);
        return ResponseEntity.ok(RouteMapper.toResponseDto(route));
    }
}
