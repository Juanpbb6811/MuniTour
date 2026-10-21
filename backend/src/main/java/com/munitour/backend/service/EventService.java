package com.munitour.backend.service;

import com.munitour.backend.entity.CulturalEvent;
import com.munitour.backend.exception.ResourceNotFoundException;
import com.munitour.backend.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;

    public List<CulturalEvent> getAll() {
        return eventRepository.findAll();
    }

    public List<CulturalEvent> getByFilters(String destinationId, Integer day) {
        if (StringUtils.hasText(destinationId) && day != null) {
            return eventRepository.findByDestinationIdAndDay(destinationId, day);
        }
        if (StringUtils.hasText(destinationId)) {
            return eventRepository.findByDestinationId(destinationId);
        }
        if (day != null) {
            return eventRepository.findByDay(day);
        }
        return eventRepository.findAll();
    }

    public CulturalEvent getById(String id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Evento no encontrado con id: " + id));
    }

    public CulturalEvent create(CulturalEvent event) {
        return eventRepository.save(event);
    }
}
