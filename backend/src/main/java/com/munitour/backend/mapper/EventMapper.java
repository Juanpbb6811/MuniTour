package com.munitour.backend.mapper;

import com.munitour.backend.dto.request.EventRequestDto;
import com.munitour.backend.dto.response.EventResponseDto;
import com.munitour.backend.entity.CulturalEvent;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EventMapper {

    public static CulturalEvent toEntity(EventRequestDto dto) {
        if (dto == null) return null;

        return CulturalEvent.builder()
                .id(StringUtils.hasText(dto.getId()) ? dto.getId() : UUID.randomUUID().toString())
                .destinationId(StringUtils.hasText(dto.getDestinationId()) ? dto.getDestinationId() : "cajica")
                .day(dto.getDay() != null ? dto.getDay() : 1)
                .dayName(StringUtils.hasText(dto.getDayName()) ? dto.getDayName() : "Lun")
                .monthYear(StringUtils.hasText(dto.getMonthYear()) ? dto.getMonthYear() : "Septiembre 2026")
                .title(dto.getTitle())
                .time(dto.getTime())
                .duration(dto.getDuration())
                .place(dto.getPlace())
                .isFree(dto.getIsFree() != null ? dto.getIsFree() : true)
                .emoji(StringUtils.hasText(dto.getEmoji()) ? dto.getEmoji() : "🎭")
                .category(StringUtils.hasText(dto.getCategory()) ? dto.getCategory() : "Cultura")
                .description(dto.getDescription())
                .build();
    }

    public static EventResponseDto toResponseDto(CulturalEvent entity) {
        if (entity == null) return null;

        return EventResponseDto.builder()
                .id(entity.getId())
                .destinationId(entity.getDestinationId())
                .day(entity.getDay())
                .dayName(entity.getDayName())
                .monthYear(entity.getMonthYear() != null ? entity.getMonthYear() : "Septiembre 2026")
                .title(entity.getTitle())
                .time(entity.getTime())
                .duration(entity.getDuration())
                .place(entity.getPlace())
                .isFree(entity.getIsFree() != null ? entity.getIsFree() : true)
                .emoji(entity.getEmoji())
                .category(entity.getCategory() != null ? entity.getCategory() : "Cultura")
                .description(entity.getDescription() != null ? entity.getDescription() : "")
                .build();
    }

    public static List<EventResponseDto> toResponseDtoList(List<CulturalEvent> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(EventMapper::toResponseDto).collect(Collectors.toList());
    }
}
