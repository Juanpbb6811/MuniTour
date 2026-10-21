package com.munitour.backend.controller;

import com.munitour.backend.dto.request.ReviewRequestDto;
import com.munitour.backend.dto.response.ReviewResponseDto;
import com.munitour.backend.entity.Review;
import com.munitour.backend.mapper.ReviewMapper;
import com.munitour.backend.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/reviews", "/api/v1/reviews"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Reseñas", description = "Comentarios, calificaciones y recomendaciones de la comunidad viajera")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Listar reseñas (opcionalmente filtradas por id del lugar)")
    public ResponseEntity<List<ReviewResponseDto>> getReviews(
            @RequestParam(required = false) String placeId
    ) {
        List<Review> reviews = reviewService.getByPlaceId(placeId);
        return ResponseEntity.ok(ReviewMapper.toResponseDtoList(reviews));
    }

    @GetMapping("/{placeId}")
    @Operation(summary = "Obtener reseñas para un lugar específico")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByPlaceId(@PathVariable String placeId) {
        List<Review> reviews = reviewService.getByPlaceId(placeId);
        return ResponseEntity.ok(ReviewMapper.toResponseDtoList(reviews));
    }

    @PostMapping
    @Operation(summary = "Publicar una reseña sobre un lugar o experiencia")
    public ResponseEntity<ReviewResponseDto> submitReview(@RequestBody ReviewRequestDto request) {
        Review entity = ReviewMapper.toEntity(request);
        Review saved = reviewService.create(entity);
        return ResponseEntity.ok(ReviewMapper.toResponseDto(saved));
    }
}
