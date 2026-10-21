package com.munitour.backend.mapper;

import com.munitour.backend.dto.request.ReviewRequestDto;
import com.munitour.backend.dto.response.ReviewResponseDto;
import com.munitour.backend.entity.Review;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class ReviewMapper {

    public static Review toEntity(ReviewRequestDto dto) {
        if (dto == null) return null;

        return Review.builder()
                .id(StringUtils.hasText(dto.getId()) ? dto.getId() : "r_" + UUID.randomUUID().toString().substring(0, 8))
                .placeId(dto.getPlaceId())
                .authorName(StringUtils.hasText(dto.getAuthorName()) ? dto.getAuthorName() : "Viajero Sabanero")
                .rating(dto.getRating() != null ? dto.getRating() : 5)
                .date(StringUtils.hasText(dto.getDate()) ? dto.getDate() : "Hoy")
                .comment(dto.getComment())
                .serviceHighlight(StringUtils.hasText(dto.getServiceHighlight()) ? dto.getServiceHighlight() : "Excelente atención")
                .coffeeRating(dto.getCoffeeRating() != null ? dto.getCoffeeRating() : 5)
                .build();
    }

    public static ReviewResponseDto toResponseDto(Review entity) {
        if (entity == null) return null;

        return ReviewResponseDto.builder()
                .id(entity.getId())
                .placeId(entity.getPlaceId())
                .authorName(entity.getAuthorName())
                .rating(entity.getRating())
                .date(entity.getDate() != null ? entity.getDate() : "Hoy")
                .comment(entity.getComment() != null ? entity.getComment() : "")
                .serviceHighlight(entity.getServiceHighlight() != null ? entity.getServiceHighlight() : "")
                .coffeeRating(entity.getCoffeeRating() != null ? entity.getCoffeeRating() : 5)
                .build();
    }

    public static List<ReviewResponseDto> toResponseDtoList(List<Review> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(ReviewMapper::toResponseDto).collect(Collectors.toList());
    }
}
