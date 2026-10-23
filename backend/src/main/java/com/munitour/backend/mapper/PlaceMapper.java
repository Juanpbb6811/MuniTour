package com.munitour.backend.mapper;

import com.munitour.backend.dto.request.OperativeDetailsRequestDto;
import com.munitour.backend.dto.request.PlaceRequestDto;
import com.munitour.backend.dto.response.OperativeDetailsResponseDto;
import com.munitour.backend.dto.response.PlaceResponseDto;
import com.munitour.backend.entity.Place;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class PlaceMapper {

    public static Place toEntity(PlaceRequestDto dto) {
        if (dto == null) return null;

        OperativeDetailsRequestDto op = dto.getOperative();

        return Place.builder()
                .id(StringUtils.hasText(dto.getId()) ? dto.getId() : UUID.randomUUID().toString())
                .destinationId(StringUtils.hasText(dto.getDestinationId()) ? dto.getDestinationId() : "cajica")
                .municipality(StringUtils.hasText(dto.getMunicipality()) ? dto.getMunicipality() : "Cajicá")
                .kind(dto.getKind())
                .name(dto.getName())
                .tag(dto.getTag())
                .emoji(StringUtils.hasText(dto.getEmoji()) ? dto.getEmoji() : "📍")
                .stars(dto.getStars() != null ? dto.getStars().intValue() : 5)
                .reviewCount(dto.getReviewCount() != null ? dto.getReviewCount() : 0)
                .latitude(dto.getLatitude())
                .longitude(dto.getLongitude())
                .xRatio(dto.getXRatio() != null ? dto.getXRatio().floatValue() : 0.5f)
                .yRatio(dto.getYRatio() != null ? dto.getYRatio().floatValue() : 0.5f)
                .address(dto.getAddress())
                .distanceKm(dto.getDistanceKm() != null ? dto.getDistanceKm().floatValue() : 0.8f)
                .imageUrl(dto.getImageUrl())
                .coffeeMethods(op != null && op.getCoffeeMethods() != null ? String.join(",", op.getCoffeeMethods()) : null)
                .coffeeOrigin(op != null ? op.getCoffeeOrigin() : null)
                .baristaSpecialty(op != null ? op.getBaristaSpecialty() : null)
                .tableServiceDynamics(op != null ? op.getTableServiceDynamics() : "Servicio a la mesa completo")
                .estimatedWaitMinutes(op != null ? op.getEstimatedWaitMinutes() : "10 - 15 min")
                .businessHours(op != null ? op.getBusinessHours() : "Lun - Dom: 7:00 a.m. - 8:30 p.m.")
                .priceTier(op != null && StringUtils.hasText(op.getPriceTier()) ? op.getPriceTier() : "$$")
                .paymentMethods(op != null && op.getPaymentMethods() != null ? String.join(",", op.getPaymentMethods()) : null)
                .hasWifi(op != null && op.getHasWifi() != null ? op.getHasWifi() : true)
                .isPetFriendly(op != null && op.getIsPetFriendly() != null ? op.getIsPetFriendly() : true)
                .outdoorTerrace(op != null && op.getOutdoorTerrace() != null ? op.getOutdoorTerrace() : true)
                .ownerStory(op != null ? op.getOwnerStory() : null)
                .build();
    }

    public static PlaceResponseDto toResponseDto(Place entity) {
        if (entity == null) return null;

        List<String> coffeeMethodsList = StringUtils.hasText(entity.getCoffeeMethods())
                ? Arrays.stream(entity.getCoffeeMethods().split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList())
                : Collections.emptyList();

        List<String> paymentMethodsList = StringUtils.hasText(entity.getPaymentMethods())
                ? Arrays.stream(entity.getPaymentMethods().split(",")).map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList())
                : Collections.emptyList();

        OperativeDetailsResponseDto operative = OperativeDetailsResponseDto.builder()
                .coffeeMethods(coffeeMethodsList)
                .coffeeOrigin(entity.getCoffeeOrigin() != null ? entity.getCoffeeOrigin() : "")
                .baristaSpecialty(entity.getBaristaSpecialty() != null ? entity.getBaristaSpecialty() : "")
                .tableServiceDynamics(entity.getTableServiceDynamics() != null ? entity.getTableServiceDynamics() : "Servicio a la mesa completo")
                .estimatedWaitMinutes(entity.getEstimatedWaitMinutes() != null ? entity.getEstimatedWaitMinutes() : "10 - 15 min")
                .businessHours(entity.getBusinessHours() != null ? entity.getBusinessHours() : "Lun - Dom: 7:00 a.m. - 8:30 p.m.")
                .priceTier(entity.getPriceTier() != null ? entity.getPriceTier() : "$$")
                .paymentMethods(paymentMethodsList)
                .hasWifi(entity.getHasWifi() != null ? entity.getHasWifi() : true)
                .isPetFriendly(entity.getIsPetFriendly() != null ? entity.getIsPetFriendly() : true)
                .outdoorTerrace(entity.getOutdoorTerrace() != null ? entity.getOutdoorTerrace() : true)
                .ownerStory(entity.getOwnerStory() != null ? entity.getOwnerStory() : "")
                .build();

        return PlaceResponseDto.builder()
                .id(entity.getId())
                .destinationId(entity.getDestinationId())
                .municipality(entity.getMunicipality())
                .kind(entity.getKind())
                .name(entity.getName())
                .tag(entity.getTag())
                .emoji(entity.getEmoji())
                .stars(entity.getStars() != null ? entity.getStars().doubleValue() : 5.0)
                .reviewCount(entity.getReviewCount())
                .latitude(entity.getLatitude())
                .longitude(entity.getLongitude())
                .xRatio(entity.getXRatio() != null ? entity.getXRatio().doubleValue() : 0.5)
                .yRatio(entity.getYRatio() != null ? entity.getYRatio().doubleValue() : 0.5)
                .address(entity.getAddress() != null ? entity.getAddress() : "")
                .distanceKm(entity.getDistanceKm() != null ? entity.getDistanceKm().doubleValue() : 0.8)
                .imageUrl(entity.getImageUrl())
                .operative(operative)
                .build();
    }

    public static List<PlaceResponseDto> toResponseDtoList(List<Place> entities) {
        if (entities == null) return Collections.emptyList();
        return entities.stream().map(PlaceMapper::toResponseDto).collect(Collectors.toList());
    }
}
