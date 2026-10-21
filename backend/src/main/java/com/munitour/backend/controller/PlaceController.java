package com.munitour.backend.controller;

import com.munitour.backend.dto.request.PlaceRequestDto;
import com.munitour.backend.dto.response.PlaceResponseDto;
import com.munitour.backend.entity.Place;
import com.munitour.backend.mapper.PlaceMapper;
import com.munitour.backend.service.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/places", "/api/v1/places"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Lugares", description = "Gestión de atractivos turísticos, culturales y gastronómicos")
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping
    @Operation(summary = "Listar lugares con filtros opcionales (municipio, destino, tipo)")
    public ResponseEntity<List<PlaceResponseDto>> getPlaces(
            @RequestParam(required = false) String destinationId,
            @RequestParam(required = false) String kind,
            @RequestParam(required = false) String municipality
    ) {
        List<Place> places = placeService.getByFilters(destinationId, kind, municipality);
        return ResponseEntity.ok(PlaceMapper.toResponseDtoList(places));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un lugar por su identificador")
    public ResponseEntity<PlaceResponseDto> getPlaceById(@PathVariable String id) {
        Place place = placeService.getById(id);
        return ResponseEntity.ok(PlaceMapper.toResponseDto(place));
    }

    @PostMapping({"", "/self-manage"})
    @Operation(summary = "Crear o autoregistrar un lugar / negocio local")
    public ResponseEntity<PlaceResponseDto> createPlace(@RequestBody PlaceRequestDto request) {
        Place entity = PlaceMapper.toEntity(request);
        Place saved = placeService.create(entity);
        return ResponseEntity.ok(PlaceMapper.toResponseDto(saved));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar información operativa de un lugar")
    public ResponseEntity<PlaceResponseDto> updatePlace(
            @PathVariable String id,
            @RequestBody PlaceRequestDto request
    ) {
        Place entity = PlaceMapper.toEntity(request);
        Place updated = placeService.update(id, entity);
        return ResponseEntity.ok(PlaceMapper.toResponseDto(updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un lugar")
    public ResponseEntity<Void> deletePlace(@PathVariable String id) {
        placeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
