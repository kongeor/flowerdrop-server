package io.github.kongeor.flowerdrop.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class WateringDto {

    private Integer id;
    private LocalDateTime createdDate;
    private String notes;
}
