package com.munitour.backend.mapper;

import com.munitour.backend.dto.response.RouteResponseDto;
import com.munitour.backend.entity.CultureRoute;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RouteMapper {

    public static RouteResponseDto toResponseDto(CultureRoute entity) {
        if (entity == null) return null;

        List<String> placesList = StringUtils.hasText(entity.getPlacesIncluded())
                ? Arrays.stream(entity.getPlacesIncluded().split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList())
                : Collections.emptyList();

        return RouteResponseDto.builder()
                .id(entity.getId())
                .destinationId(entity.getDestinationId())
                .title(entity.getTitle())
                .subtitle(entity.getSubtitle() != null ? entity.getSubtitle() : "")
                .durationText(entity.getDurationText() != null ? entity.getDurationText() : "")
                .distanceText(entity.getDistanceText() != null ? entity.getDistanceText() : "")
                .difficulty(entity.getDifficulty() != null ? entity.getDifficulty() : "Fácil")
                .placesIncluded(placesList)
                .audioTitle(entity.getAudioTitle() != null ? entity.getAudioTitle() : "")
                .audioDurationSec(entity.getAudioDurationSec() != null ? entity.getAudioDurationSec() : 180)
                .audioNarrator(entity.getAudioNarrator() != null ? entity.getAudioNarrator() : "Guía Patrimonial Local")
                .audioTranscript(entity.getAudioTranscript() != null ? entity.getAudioTranscript() : "")
                .build();
    }

    public static List<RouteResponseDto> toResponseDtoList(List<CultureRoute> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(RouteMapper::toResponseDto).collect(Collectors.toList());
    }
}
