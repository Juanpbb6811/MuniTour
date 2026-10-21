package com.munitour.backend.service;

import com.munitour.backend.entity.Place;
import com.munitour.backend.entity.Review;
import com.munitour.backend.exception.ResourceNotFoundException;
import com.munitour.backend.repository.PlaceRepository;
import com.munitour.backend.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PlaceRepository placeRepository;

    public List<Review> getAll() {
        return reviewRepository.findAll();
    }

    public List<Review> getByPlaceId(String placeId) {
        if (StringUtils.hasText(placeId)) {
            return reviewRepository.findByPlaceId(placeId);
        }
        return reviewRepository.findAll();
    }

    public Review getById(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Reseña no encontrada con id: " + id));
    }

    public Review create(Review review) {
        Review saved = reviewRepository.save(review);

        // Actualizar estadísticas del lugar si existe
        if (StringUtils.hasText(review.getPlaceId())) {
            Optional<Place> placeOpt = placeRepository.findById(review.getPlaceId());
            if (placeOpt.isPresent()) {
                Place place = placeOpt.get();
                int currentReviews = place.getReviewCount() != null ? place.getReviewCount() : 0;
                place.setReviewCount(currentReviews + 1);
                placeRepository.save(place);
            }
        }

        return saved;
    }
}
