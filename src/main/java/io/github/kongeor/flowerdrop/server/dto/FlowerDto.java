package io.github.kongeor.flowerdrop.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class FlowerDto {

    private Integer id;
    private String name;
    private String description;
//    private List<WateringDto> waterings = new ArrayList<>();
}
