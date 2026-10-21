package com.munitour.backend.service;

import com.munitour.backend.entity.CultureRoute;
import com.munitour.backend.exception.ResourceNotFoundException;
import com.munitour.backend.repository.CultureRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RouteService {

    private final CultureRouteRepository routeRepository;

    public List<CultureRoute> getAll() {
        return routeRepository.findAll();
    }

    public List<CultureRoute> getByDestinationId(String destinationId) {
        if (StringUtils.hasText(destinationId)) {
            return routeRepository.findByDestinationId(destinationId);
        }
        return routeRepository.findAll();
    }

    public CultureRoute getById(String id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta no encontrada con id: " + id));
    }

    public CultureRoute create(CultureRoute route) {
        return routeRepository.save(route);
    }
}
