package mappers;

import dto.UserDto;
import entities.User;

public class UserMapper implements Imapper<User, UserDto> {
    @Override
    public UserDto map(User user) {
        return new UserDto(user.getId(),
                user.getLogin(),
                user.getBalance(),
                user.getCardNumber(),
                user.getRole(),
                null);
    }
}
