package com.example.tasklist.web.mappers;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring") // это значит, что создадится компонент этого маппера, и мы его сможем вызывать как класс через autowired
public interface UserMapper {

    // на этом все mapstruct сам все смапит, и будет готово
    UserDto toDto(User user);

    User userToEntity(UserDto userDto);

}
