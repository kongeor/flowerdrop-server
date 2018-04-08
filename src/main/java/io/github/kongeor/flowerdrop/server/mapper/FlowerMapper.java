package io.github.kongeor.flowerdrop.server.mapper;

import io.github.kongeor.flowerdrop.server.core.Flower;
import io.github.kongeor.flowerdrop.server.dto.FlowerDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FlowerMapper {

    FlowerMapper INSTANCE = Mappers.getMapper(FlowerMapper.class);

    @Mapping(target = "id", source = "id")
    FlowerDto flowerToDto(Flower flower);

    List<FlowerDto> flowersToDtos(List<Flower> flowers);
}
