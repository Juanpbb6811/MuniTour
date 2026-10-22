package com.munitour.backend.service;

import com.munitour.backend.dto.request.UserUpdateRequestDto;
import com.munitour.backend.entity.AppUser;
import com.munitour.backend.exception.ResourceNotFoundException;
import com.munitour.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public AppUser getById(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id: " + id));
    }

    public AppUser getByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con email: " + email));
    }

    public AppUser updateProfile(String email, UserUpdateRequestDto dto) {
        AppUser user = getByEmail(email);

        if (StringUtils.hasText(dto.getName())) {
            user.setFullName(dto.getName());
        }
        if (StringUtils.hasText(dto.getPhone())) {
            user.setPhone(dto.getPhone());
        }
        if (StringUtils.hasText(dto.getBio())) {
            user.setBio(dto.getBio());
        }
        if (StringUtils.hasText(dto.getPreferredTransport())) {
            user.setPreferredTransport(dto.getPreferredTransport());
        }
        if (StringUtils.hasText(dto.getOriginCity())) {
            user.setOriginCity(dto.getOriginCity());
        }
        if (StringUtils.hasText(dto.getAvatarEmoji())) {
            user.setAvatarEmoji(dto.getAvatarEmoji());
        }
        if (StringUtils.hasText(dto.getFavoriteCoffeeMethod())) {
            user.setFavoriteCoffeeMethod(dto.getFavoriteCoffeeMethod());
        }

        return userRepository.save(user);
    }
}
