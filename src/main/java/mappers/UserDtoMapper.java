package mappers;

import dto.UserDto;
import entities.User;

import java.util.Collections;

public class UserDtoMapper implements Imapper<UserDto, User> {
    @Override
    public User map(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getLogin(),
                null,
                userDto.getBalance(),
                userDto.getCardNumber(),
                userDto.getRole(),
                Collections.emptyList());
    }
}
