package io.github.kongeor.flowerdrop.server.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class UserDto {

    private Integer id;
    private String name;
    private List<FlowerDto> flowers = new ArrayList<>();
}
