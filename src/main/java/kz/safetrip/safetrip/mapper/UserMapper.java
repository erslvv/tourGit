package kz.safetrip.safetrip.mapper;

import kz.safetrip.safetrip.config.GlobalMapperConfig;
import kz.safetrip.safetrip.model.dto.UserDto;
import kz.safetrip.safetrip.model.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(config = GlobalMapperConfig.class)
public interface UserMapper {

    UserDto toDto(User entity);

    List<UserDto> toDtoList(List<User> entities);

    User toEntity(UserDto dto);

    List<User> toEntityList(List<UserDto> dtos);
}
