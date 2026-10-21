package com.munitour.backend.service;

import com.munitour.backend.entity.InterMunicipalRoute;
import com.munitour.backend.exception.ResourceNotFoundException;
import com.munitour.backend.repository.InterMunicipalRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InterMunicipalRouteService {

    private final InterMunicipalRouteRepository routeRepository;

    public List<InterMunicipalRoute> getAll() {
        return routeRepository.findAll();
    }

    public InterMunicipalRoute getById(String id) {
        return routeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta intermunicipal no encontrada con id: " + id));
    }

    public InterMunicipalRoute create(InterMunicipalRoute route) {
        return routeRepository.save(route);
    }
}
