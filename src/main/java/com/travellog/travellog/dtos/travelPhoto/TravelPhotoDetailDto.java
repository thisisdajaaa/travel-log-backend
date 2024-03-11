package com.travellog.travellog.dtos.travelPhoto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TravelPhotoDetailDto {
    private Integer id;

    private String photoUrl;

    private String description;

    private Integer travelLogId;
}
