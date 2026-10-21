package com.munitour.backend.controller;

import com.munitour.backend.dto.request.DishRequestDto;
import com.munitour.backend.dto.response.DishResponseDto;
import com.munitour.backend.entity.Dish;
import com.munitour.backend.mapper.DishMapper;
import com.munitour.backend.service.DishService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/dishes", "/api/v1/dishes"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Sabores y Gastronomía", description = "Platos típicos, café de especialidad y delicias de la Sabana")
public class DishController {

    private final DishService dishService;

    @GetMapping
    @Operation(summary = "Listar platos gastronómicos con filtros opcionales")
    public ResponseEntity<List<DishResponseDto>> getDishes(
            @RequestParam(required = false) String destinationId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String placeId
    ) {
        List<Dish> dishes;
        if (StringUtils.hasText(placeId)) {
            dishes = dishService.getByPlaceId(placeId);
        } else {
            dishes = dishService.getByFilters(destinationId, category);
        }
        return ResponseEntity.ok(DishMapper.toResponseDtoList(dishes));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de un plato por id")
    public ResponseEntity<DishResponseDto> getDishById(@PathVariable String id) {
        Dish dish = dishService.getById(id);
        return ResponseEntity.ok(DishMapper.toResponseDto(dish));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo plato o producto gastronómico")
    public ResponseEntity<DishResponseDto> createDish(@RequestBody DishRequestDto request) {
        Dish entity = DishMapper.toEntity(request);
        Dish saved = dishService.create(entity);
        return ResponseEntity.ok(DishMapper.toResponseDto(saved));
    }
}
