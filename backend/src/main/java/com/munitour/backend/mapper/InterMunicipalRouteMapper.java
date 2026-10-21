package com.munitour.backend.mapper;

import com.munitour.backend.dto.response.InterMunicipalRouteResponseDto;
import com.munitour.backend.entity.InterMunicipalRoute;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InterMunicipalRouteMapper {

    public static InterMunicipalRouteResponseDto toResponseDto(InterMunicipalRoute entity) {
        if (entity == null) return null;

        List<String> municipalitiesList = StringUtils.hasText(entity.getMunicipalities())
                ? Arrays.stream(entity.getMunicipalities().split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList())
                : Collections.emptyList();

        List<String> keyStopsList = StringUtils.hasText(entity.getKeyStops())
                ? Arrays.stream(entity.getKeyStops().split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList())
                : Collections.emptyList();

        return InterMunicipalRouteResponseDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .subtitle(entity.getSubtitle() != null ? entity.getSubtitle() : "")
                .theme(entity.getTheme() != null ? entity.getTheme() : "")
                .municipalities(municipalitiesList)
                .totalDistanceKm(entity.getTotalDistanceKm() != null ? entity.getTotalDistanceKm() : 0.0)
                .estimatedDuration(entity.getEstimatedDuration() != null ? entity.getEstimatedDuration() : "")
                .transportMode(entity.getTransportMode() != null ? entity.getTransportMode() : "")
                .difficulty(entity.getDifficulty() != null ? entity.getDifficulty() : "")
                .keyStops(keyStopsList)
                .description(entity.getDescription() != null ? entity.getDescription() : "")
                .highlightTip(entity.getHighlightTip() != null ? entity.getHighlightTip() : "")
                .emoji(entity.getEmoji() != null ? entity.getEmoji() : "🛤️")
                .build();
    }

    public static List<InterMunicipalRouteResponseDto> toResponseDtoList(List<InterMunicipalRoute> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(InterMunicipalRouteMapper::toResponseDto).collect(Collectors.toList());
    }
}
