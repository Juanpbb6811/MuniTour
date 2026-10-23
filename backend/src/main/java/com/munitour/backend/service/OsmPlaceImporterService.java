package com.munitour.backend.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.munitour.backend.dto.response.OsmImportResultDto;
import com.munitour.backend.dto.response.PlaceResponseDto;
import com.munitour.backend.entity.Place;
import com.munitour.backend.mapper.PlaceMapper;
import com.munitour.backend.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.Normalizer;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OsmPlaceImporterService {

    private final PlaceRepository placeRepository;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    private static final String OVERPASS_API_URL = "https://overpass-api.de/api/interpreter";

    // Unsplash High-Definition Royalty-Free Food Photography by Culinary Theme
    private static final String IMG_CAFE = "https://images.unsplash.com/photo-1501339847302-ac426a4a7cbb?auto=format&fit=crop&w=800&q=80";
    private static final String IMG_PIZZA = "https://images.unsplash.com/photo-1513104890138-7c749659a591?auto=format&fit=crop&w=800&q=80";
    private static final String IMG_GRILL = "https://images.unsplash.com/photo-1544025162-d76694265947?auto=format&fit=crop&w=800&q=80";
    private static final String IMG_BURGER = "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?auto=format&fit=crop&w=800&q=80";
    private static final String IMG_ICE_CREAM = "https://images.unsplash.com/photo-1501443762994-82bd5dace89a?auto=format&fit=crop&w=800&q=80";
    private static final String IMG_TRADITIONAL = "https://images.unsplash.com/photo-1555396273-367ea4eb4db5?auto=format&fit=crop&w=800&q=80";
    private static final String IMG_PASTA = "https://images.unsplash.com/photo-1551183053-bf91a1d81141?auto=format&fit=crop&w=800&q=80";
    private static final String IMG_ASIAN = "https://images.unsplash.com/photo-1569718212165-3a8278d5f624?auto=format&fit=crop&w=800&q=80";
    private static final String IMG_SEAFOOD = "https://images.unsplash.com/photo-1534422298391-e4f8c172dddb?auto=format&fit=crop&w=800&q=80";
    private static final String IMG_GENERAL = "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?auto=format&fit=crop&w=800&q=80";

    @Transactional
    public OsmImportResultDto importRestaurantsForMunicipality(
            String destinationId,
            String municipality,
            Double centerLat,
            Double centerLon,
            Integer radiusMeters
    ) {
        String destId = (destinationId != null && !destinationId.isBlank()) ? destinationId.toLowerCase() : "cajica";
        String mun = (municipality != null && !municipality.isBlank()) ? municipality : "Cajicá";
        double lat = centerLat != null ? centerLat : 4.9186;
        double lon = centerLon != null ? centerLon : -74.0259;
        int radius = radiusMeters != null ? radiusMeters : 5000;

        log.info("Iniciando importación OpenStreetMap para {} (radio: {}m)...", mun, radius);

        String overpassQuery = String.format(
                Locale.US,
                "[out:json][timeout:30];" +
                "(" +
                "  node[\"amenity\"~\"restaurant|cafe|fast_food|ice_cream|bar\"](around:%d,%.4f,%.4f);" +
                "  way[\"amenity\"~\"restaurant|cafe|fast_food|ice_cream|bar\"](around:%d,%.4f,%.4f);" +
                ");" +
                "out tags center 60;",
                radius, lat, lon, radius, lat, lon
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.set("User-Agent", "MuniTourApp/1.0 (munitour.co - tourism project)");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("data", overpassQuery);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

        int totalFound = 0;
        int importedCount = 0;
        int updatedCount = 0;
        List<Place> savedPlaces = new ArrayList<>();

        try {
            String rawJson = restTemplate.postForObject(OVERPASS_API_URL, request, String.class);
            if (rawJson == null) {
                return OsmImportResultDto.builder()
                        .municipality(mun)
                        .totalFound(0)
                        .importedCount(0)
                        .updatedCount(0)
                        .message("Respuesta vacía desde Overpass API")
                        .places(Collections.emptyList())
                        .build();
            }

            JsonNode rootNode = objectMapper.readTree(rawJson);
            JsonNode elementsNode = rootNode.path("elements");

            if (!elementsNode.isArray()) {
                return OsmImportResultDto.builder()
                        .municipality(mun)
                        .totalFound(0)
                        .importedCount(0)
                        .updatedCount(0)
                        .message("No se encontraron elementos en la respuesta")
                        .places(Collections.emptyList())
                        .build();
            }

            totalFound = elementsNode.size();

            for (JsonNode element : elementsNode) {
                JsonNode tags = element.path("tags");
                if (tags.isMissingNode()) continue;

                String name = tags.path("name").asText(null);
                if (name == null || name.trim().isBlank()) continue;

                name = name.trim();
                Double placeLat = element.hasNonNull("lat") ? element.get("lat").asDouble() :
                        (element.has("center") && element.get("center").hasNonNull("lat") ? element.get("center").get("lat").asDouble() : null);
                Double placeLon = element.hasNonNull("lon") ? element.get("lon").asDouble() :
                        (element.has("center") && element.get("center").hasNonNull("lon") ? element.get("center").get("lon").asDouble() : null);

                if (placeLat == null || placeLon == null) continue;

                String amenity = tags.path("amenity").asText("");
                String cuisine = tags.path("cuisine").asText("");
                String openingHours = tags.path("opening_hours").asText("");
                String street = tags.path("addr:street").asText("");
                String housenumber = tags.path("addr:housenumber").asText("");

                String address = buildAddress(street, housenumber, mun);
                PlaceCategoryMetadata meta = classifyPlace(name, amenity, cuisine);

                String slugId = "osm-" + slugify(name);
                Optional<Place> existingOpt = placeRepository.findById(slugId);

                double distanceKm = calculateDistanceKm(lat, lon, placeLat, placeLon);

                Place place;
                if (existingOpt.isPresent()) {
                    place = existingOpt.get();
                    place.setLatitude(placeLat);
                    place.setLongitude(placeLon);
                    if (place.getImageUrl() == null || place.getImageUrl().isBlank()) {
                        place.setImageUrl(meta.imageUrl);
                    }
                    if (place.getAddress() == null || place.getAddress().isBlank()) {
                        place.setAddress(address);
                    }
                    updatedCount++;
                } else {
                    int stars = 4 + (Math.abs(name.hashCode()) % 2); // 4 or 5 stars
                    int reviews = 15 + (Math.abs(name.hashCode()) % 85);

                    place = Place.builder()
                            .id(slugId)
                            .destinationId(destId)
                            .municipality(mun)
                            .kind("sabor")
                            .name(name)
                            .tag(meta.tag)
                            .emoji(meta.emoji)
                            .stars(stars)
                            .reviewCount(reviews)
                            .latitude(placeLat)
                            .longitude(placeLon)
                            .xRatio(0.5f)
                            .yRatio(0.5f)
                            .address(address)
                            .distanceKm((float) distanceKm)
                            .imageUrl(meta.imageUrl)
                            .businessHours(openingHours.isBlank() ? "Mar - Dom: 11:30 a.m. - 8:30 p.m." : openingHours)
                            .estimatedWaitMinutes("10 - 20 min")
                            .priceTier(meta.priceTier)
                            .paymentMethods("Efectivo,Nequi,Daviplata,Tarjetas")
                            .hasWifi(true)
                            .isPetFriendly(true)
                            .outdoorTerrace(true)
                            .tableServiceDynamics("Servicio a la mesa y pedidos para llevar")
                            .ownerStory("Emprendimiento gastronómico local en " + mun + ", Cundinamarca.")
                            .coffeeMethods(meta.coffeeMethods)
                            .build();

                    importedCount++;
                }

                savedPlaces.add(placeRepository.save(place));
            }

            log.info("Importación completada para {}: {} nuevos, {} actualizados de {} encontrados.",
                    mun, importedCount, updatedCount, totalFound);

        } catch (Exception e) {
            log.error("Error al consultar Overpass API para OpenStreetMap: {}", e.getMessage(), e);
            return OsmImportResultDto.builder()
                    .municipality(mun)
                    .totalFound(0)
                    .importedCount(0)
                    .updatedCount(0)
                    .message("Error al importar desde OpenStreetMap: " + e.getMessage())
                    .places(Collections.emptyList())
                    .build();
        }

        List<PlaceResponseDto> responseDtos = PlaceMapper.toResponseDtoList(savedPlaces);

        return OsmImportResultDto.builder()
                .municipality(mun)
                .totalFound(totalFound)
                .importedCount(importedCount)
                .updatedCount(updatedCount)
                .message(String.format("Importación exitosa: %d restaurantes y cafeterías listos en el mapa de %s", savedPlaces.size(), mun))
                .places(responseDtos)
                .build();
    }

    private PlaceCategoryMetadata classifyPlace(String name, String amenity, String cuisine) {
        String combined = (name + " " + amenity + " " + cuisine).toLowerCase();

        if (combined.contains("cafe") || combined.contains("café") || combined.contains("coffee") || combined.contains("barista") || combined.contains("espresso") || combined.contains("tintico") || combined.contains("greka")) {
            return new PlaceCategoryMetadata("☕", "Café de origen & Barismo", IMG_CAFE, "$$", "Filtrado V60,Chemex,Prensa Francesa,Espresso");
        }
        if (combined.contains("pizza")) {
            return new PlaceCategoryMetadata("🍕", "Pizza artesanal & Horno de leña", IMG_PIZZA, "$$", null);
        }
        if (combined.contains("asado") || combined.contains("parrilla") || combined.contains("carne") || combined.contains("fuego") || combined.contains("chorizo") || combined.contains("rancho")) {
            return new PlaceCategoryMetadata("🥩", "Parrilla al carbón & Asados", IMG_GRILL, "$$$", null);
        }
        if (combined.contains("burger") || combined.contains("hamburg") || combined.contains("wings") || combined.contains("alitas") || combined.contains("fast_food") || combined.contains("q'banos")) {
            return new PlaceCategoryMetadata("🍔", "Hamburguesas gourmet & Comida rápida", IMG_BURGER, "$$", null);
        }
        if (combined.contains("helad") || combined.contains("ice_cream") || combined.contains("postre") || combined.contains("praline") || combined.contains("crepes")) {
            return new PlaceCategoryMetadata("🍦", "Heladería artesanal & Postres", IMG_ICE_CREAM, "$", null);
        }
        if (combined.contains("empanada") || combined.contains("arepa") || combined.contains("almojabana") || combined.contains("almojábana") || combined.contains("amasijo")) {
            return new PlaceCategoryMetadata("🥟", "Amasijos sabaneros & Antojos típicos", IMG_TRADITIONAL, "$", null);
        }
        if (combined.contains("pasta") || combined.contains("italian") || combined.contains("mezzanotte")) {
            return new PlaceCategoryMetadata("🍝", "Cocina italiana & Pastas frescas", IMG_PASTA, "$$", null);
        }
        if (combined.contains("ramen") || combined.contains("sushi") || combined.contains("japanese") || combined.contains("asian") || combined.contains("asiatic")) {
            return new PlaceCategoryMetadata("🍜", "Gastronomía asiática & Ramen", IMG_ASIAN, "$$", null);
        }
        if (combined.contains("pescad") || combined.contains("mariscos") || combined.contains("seafood")) {
            return new PlaceCategoryMetadata("🐟", "Pescados & Mariscos frescos", IMG_SEAFOOD, "$$", null);
        }

        return new PlaceCategoryMetadata("🍽️", "Restaurante típico & Cocina sabanera", IMG_GENERAL, "$$", null);
    }

    private String buildAddress(String street, String housenumber, String municipality) {
        if (!street.isBlank()) {
            if (!housenumber.isBlank()) {
                return street + " #" + housenumber + ", " + municipality;
            }
            return street + ", " + municipality;
        }
        return municipality + ", Cundinamarca";
    }

    private double calculateDistanceKm(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;
        return Math.round(distance * 10.0) / 10.0;
    }

    private String slugify(String input) {
        if (input == null) return UUID.randomUUID().toString().substring(0, 8);
        String nowhitespace = input.trim().replaceAll("\\s+", "-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = normalized.replaceAll("[^\\w\\-]", "").toLowerCase();
        return slug.length() > 40 ? slug.substring(0, 40) : slug;
    }

    private record PlaceCategoryMetadata(
            String emoji,
            String tag,
            String imageUrl,
            String priceTier,
            String coffeeMethods
    ) {}
}
