package com.munitour.backend.service;

import com.munitour.backend.entity.Dish;
import com.munitour.backend.exception.ResourceNotFoundException;
import com.munitour.backend.repository.DishRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DishService {

    private final DishRepository dishRepository;

    public List<Dish> getAll() {
        return dishRepository.findAll();
    }

    public List<Dish> getByFilters(String destinationId, String category) {
        if (StringUtils.hasText(destinationId) && StringUtils.hasText(category)) {
            return dishRepository.findByDestinationIdAndCategory(destinationId, category);
        }
        if (StringUtils.hasText(destinationId)) {
            return dishRepository.findByDestinationId(destinationId);
        }
        if (StringUtils.hasText(category)) {
            return dishRepository.findByCategory(category);
        }
        return dishRepository.findAll();
    }

    public List<Dish> getByPlaceId(String placeId) {
        return dishRepository.findByPlaceId(placeId);
    }

    public Dish getById(String id) {
        return dishRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Plato no encontrado con id: " + id));
    }

    public Dish create(Dish dish) {
        return dishRepository.save(dish);
    }
}
