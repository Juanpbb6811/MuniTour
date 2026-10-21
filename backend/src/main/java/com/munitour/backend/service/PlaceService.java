package com.munitour.backend.service;

import com.munitour.backend.entity.Place;
import com.munitour.backend.exception.ResourceNotFoundException;
import com.munitour.backend.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PlaceService {

    private final PlaceRepository placeRepository;

    public List<Place> getAll() {
        return placeRepository.findAll();
    }

    public List<Place> getByFilters(String destinationId, String kind, String municipality) {
        if (StringUtils.hasText(municipality)) {
            return placeRepository.findByMunicipality(municipality);
        }
        if (StringUtils.hasText(destinationId) && StringUtils.hasText(kind)) {
            return placeRepository.findByDestinationIdAndKind(destinationId, kind);
        }
        if (StringUtils.hasText(destinationId)) {
            return placeRepository.findByDestinationId(destinationId);
        }
        return placeRepository.findAll();
    }

    public Place getById(String id) {
        return placeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lugar no encontrado con id: " + id));
    }

    public Place create(Place place) {
        return placeRepository.save(place);
    }

    public Place update(String id, Place updated) {
        Place existing = getById(id);
        existing.setName(updated.getName());
        existing.setKind(updated.getKind());
        existing.setTag(updated.getTag());
        existing.setEmoji(updated.getEmoji());
        existing.setAddress(updated.getAddress());
        existing.setStars(updated.getStars());
        existing.setReviewCount(updated.getReviewCount());
        existing.setLatitude(updated.getLatitude());
        existing.setLongitude(updated.getLongitude());
        existing.setCoffeeMethods(updated.getCoffeeMethods());
        existing.setCoffeeOrigin(updated.getCoffeeOrigin());
        existing.setBaristaSpecialty(updated.getBaristaSpecialty());
        existing.setTableServiceDynamics(updated.getTableServiceDynamics());
        existing.setEstimatedWaitMinutes(updated.getEstimatedWaitMinutes());
        existing.setBusinessHours(updated.getBusinessHours());
        existing.setPriceTier(updated.getPriceTier());
        existing.setPaymentMethods(updated.getPaymentMethods());
        existing.setHasWifi(updated.getHasWifi());
        existing.setIsPetFriendly(updated.getIsPetFriendly());
        existing.setOutdoorTerrace(updated.getOutdoorTerrace());
        existing.setOwnerStory(updated.getOwnerStory());
        return placeRepository.save(existing);
    }

    public void delete(String id) {
        Place place = getById(id);
        placeRepository.delete(place);
    }
}
