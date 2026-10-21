package com.munitour.backend.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO anidado con detalles operativos de un lugar (cafetería, restaurante, etc.).
 * Incluye información sobre métodos de café, servicios y comodidades.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperativeDetailsRequestDto {

    @JsonProperty("coffee_methods")
    private List<String> coffeeMethods;

    @JsonProperty("coffee_origin")
    private String coffeeOrigin;

    @JsonProperty("barista_specialty")
    private String baristaSpecialty;

    @JsonProperty("table_service_dynamics")
    private String tableServiceDynamics;

    @JsonProperty("estimated_wait_minutes")
    private String estimatedWaitMinutes;

    @JsonProperty("business_hours")
    private String businessHours;

    @JsonProperty("price_tier")
    private String priceTier;

    @JsonProperty("payment_methods")
    private List<String> paymentMethods;

    @JsonProperty("has_wifi")
    private Boolean hasWifi;

    @JsonProperty("is_pet_friendly")
    private Boolean isPetFriendly;

    @JsonProperty("outdoor_terrace")
    private Boolean outdoorTerrace;

    @JsonProperty("owner_story")
    private String ownerStory;
}
