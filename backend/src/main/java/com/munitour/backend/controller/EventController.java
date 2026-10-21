package com.munitour.backend.controller;

import com.munitour.backend.dto.request.EventRequestDto;
import com.munitour.backend.dto.response.EventResponseDto;
import com.munitour.backend.entity.CulturalEvent;
import com.munitour.backend.mapper.EventMapper;
import com.munitour.backend.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/events", "/api/v1/events"})
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
@Tag(name = "Agenda Cultural", description = "Festivales, retretas, senderismo y eventos de Sabana Centro")
public class EventController {

    private final EventService eventService;

    @GetMapping
    @Operation(summary = "Listar eventos culturales con filtros por destino o día")
    public ResponseEntity<List<EventResponseDto>> getEvents(
            @RequestParam(required = false) String destinationId,
            @RequestParam(required = false) Integer day
    ) {
        List<CulturalEvent> events = eventService.getByFilters(destinationId, day);
        return ResponseEntity.ok(EventMapper.toResponseDtoList(events));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalle de un evento cultural")
    public ResponseEntity<EventResponseDto> getEventById(@PathVariable String id) {
        CulturalEvent event = eventService.getById(id);
        return ResponseEntity.ok(EventMapper.toResponseDto(event));
    }

    @PostMapping
    @Operation(summary = "Crear un nuevo evento cultural")
    public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto request) {
        CulturalEvent entity = EventMapper.toEntity(request);
        CulturalEvent saved = eventService.create(entity);
        return ResponseEntity.ok(EventMapper.toResponseDto(saved));
    }
}
