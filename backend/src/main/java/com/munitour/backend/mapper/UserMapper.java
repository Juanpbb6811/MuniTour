package com.munitour.backend.mapper;

import com.munitour.backend.dto.response.UserResponseDto;
import com.munitour.backend.entity.AppUser;

public class UserMapper {

    public static UserResponseDto toResponseDto(AppUser entity) {
        if (entity == null) return null;

        return UserResponseDto.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .fullName(entity.getFullName())
                .originCity(entity.getOriginCity() != null ? entity.getOriginCity() : "Cajicá, Cundinamarca")
                .avatarEmoji(entity.getAvatarEmoji() != null ? entity.getAvatarEmoji() : "🧭")
                .travelerLevel(entity.getTravelerLevel() != null ? entity.getTravelerLevel() : "Explorador Cittaslow")
                .completedRoutesCount(entity.getCompletedRoutesCount() != null ? entity.getCompletedRoutesCount() : 0)
                .visitedPlacesCount(entity.getVisitedPlacesCount() != null ? entity.getVisitedPlacesCount() : 0)
                .reviewsCount(entity.getReviewsCount() != null ? entity.getReviewsCount() : 0)
                .ecoPoints(entity.getEcoPoints() != null ? entity.getEcoPoints() : 0)
                .favoriteCoffeeMethod(entity.getFavoriteCoffeeMethod() != null ? entity.getFavoriteCoffeeMethod() : "Filtrado Chemex")
                .build();
    }
}
