package com.munitour.backend.mapper;

import com.munitour.backend.dto.request.DishRequestDto;
import com.munitour.backend.dto.response.DishResponseDto;
import com.munitour.backend.entity.Dish;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class DishMapper {

    public static Dish toEntity(DishRequestDto dto) {
        if (dto == null) return null;

        return Dish.builder()
                .id(StringUtils.hasText(dto.getId()) ? dto.getId() : UUID.randomUUID().toString())
                .destinationId(StringUtils.hasText(dto.getDestinationId()) ? dto.getDestinationId() : "cajica")
                .name(dto.getName())
                .category(dto.getCategory())
                .placeId(dto.getPlaceId())
                .placeName(dto.getPlaceName())
                .description(dto.getDescription())
                .price(dto.getPrice() != null ? dto.getPrice() : 0L)
                .emoji(StringUtils.hasText(dto.getEmoji()) ? dto.getEmoji() : "🍲")
                .isSignature(dto.getIsSignature() != null ? dto.getIsSignature() : false)
                .waitTimeMin(dto.getWaitTimeMin() != null ? dto.getWaitTimeMin() : 15)
                .build();
    }

    public static DishResponseDto toResponseDto(Dish entity) {
        if (entity == null) return null;

        return DishResponseDto.builder()
                .id(entity.getId())
                .destinationId(entity.getDestinationId())
                .name(entity.getName())
                .category(entity.getCategory())
                .placeId(entity.getPlaceId())
                .placeName(entity.getPlaceName())
                .description(entity.getDescription() != null ? entity.getDescription() : "")
                .price(entity.getPrice())
                .emoji(entity.getEmoji())
                .isSignature(entity.getIsSignature() != null ? entity.getIsSignature() : false)
                .waitTimeMin(entity.getWaitTimeMin() != null ? entity.getWaitTimeMin() : 15)
                .build();
    }

    public static List<DishResponseDto> toResponseDtoList(List<Dish> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(DishMapper::toResponseDto).collect(Collectors.toList());
    }
}
