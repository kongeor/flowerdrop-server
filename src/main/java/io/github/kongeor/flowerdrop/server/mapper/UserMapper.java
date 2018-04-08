package io.github.kongeor.flowerdrop.server.mapper;

import io.github.kongeor.flowerdrop.server.core.User;
import io.github.kongeor.flowerdrop.server.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = FlowerMapper.class)
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "id", source = "id")
    UserDto userToDto(User user);
}
