package com.munitour.backend.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OsmImportResultDto {

    @JsonProperty("municipality")
    private String municipality;

    @JsonProperty("total_found")
    private int totalFound;

    @JsonProperty("imported_count")
    private int importedCount;

    @JsonProperty("updated_count")
    private int updatedCount;

    @JsonProperty("message")
    private String message;

    @JsonProperty("places")
    private List<PlaceResponseDto> places;
}
