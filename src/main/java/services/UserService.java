package services;

import dao.UserDao;
import dto.CreateUserDto;
import dto.UserDto;
import entities.User;
import exceptions.LoginAlreadyRegisteredException;
import mappers.UserMapper;

import java.util.List;
import java.util.Objects;

public class UserService {
    private final UserDao userDao = new UserDao();
    private final UserMapper userMapper = new UserMapper();

    List<UserDto> findAll() {
        return userDao.findAll().stream().map(userMapper::map).toList();
    }

    public boolean deleteById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0!");
        }
        return userDao.deleteById(id);
    }

    public UserDto findByLogin(String login) {
        if (login.isEmpty()) {
            throw new IllegalArgumentException("login is empty!");
        }
        return userMapper.map(userDao.findByLogin(login));
    }

    public UserDto findByLoginAndPassword(String login, String password) {
        if (login.isEmpty() || password.isEmpty()) {
            return null;
        }
        return userMapper.map(userDao.findByLoginAndPassword(login, password));
    }

    public boolean deleteByLogin(String login) {
        if (login.isEmpty()) {
            throw new IllegalArgumentException("login is empty");
        }
        return userDao.deleteByLogin(login);
    }

    public User createUser(CreateUserDto userDto) throws LoginAlreadyRegisteredException {
        return userDao.save(new User(null,
                userDto.getLogin(),
                userDto.getPassword(),
                userDto.getBalance(),
                userDto.getCardNumber(),
                userDto.getRole(), null));
    }

    public void updateUser(User user) {
        userDao.update(user);
    }

    public UserDto findByPassword(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("password is empty!");
        }
        return userMapper.map(userDao.findByPassword(Objects.hash(password)));
    }
}
