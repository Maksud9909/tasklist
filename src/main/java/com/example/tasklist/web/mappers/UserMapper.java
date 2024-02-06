package com.example.tasklist.web.mappers;

import com.example.tasklist.domain.user.User;
import com.example.tasklist.web.dto.user.UserDto;
import org.mapstruct.Mapper;

/**
 *  Эта аннотация говорит MapStruct, что нужно создать Spring компонент для этого маппера.
 * Это означает, что вы сможете использовать его, внедряя его как бин в другие классы с помощью @Autowired.
 */
@Mapper(componentModel = "spring") // это значит, что создадится компонент этого маппера, и мы его сможем вызывать как класс через autowired
public interface UserMapper {

    // на этом все mapstruct сам все смапит, и будет готово
    UserDto toDto(User user);// user to UserDTO

    User userToEntity(UserDto userDto);// UserDTO to User

}
