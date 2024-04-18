package mappers;

import dto.CreateUserDto;
import entities.User;

public class CreateUserMapper implements Imapper<CreateUserDto, User> {
    @Override
    public User map(CreateUserDto createUserDto) {
        return new User(null,
                createUserDto.getLogin(),
                createUserDto.getPassword(),
                createUserDto.getBalance(),
                createUserDto.getCardNumber(),
                createUserDto.getRole(),
                null);
    }
}
