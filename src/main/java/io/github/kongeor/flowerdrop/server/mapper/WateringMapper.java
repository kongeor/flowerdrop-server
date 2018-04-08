package io.github.kongeor.flowerdrop.server.mapper;

import io.github.kongeor.flowerdrop.server.core.Watering;
import io.github.kongeor.flowerdrop.server.dto.WateringDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface WateringMapper {

    WateringMapper INSTANCE = Mappers.getMapper(WateringMapper.class);

    @Mapping(target = "id")
    @Mapping(target = "createdDate")
    WateringDto wateringToDto(Watering watering);
}
