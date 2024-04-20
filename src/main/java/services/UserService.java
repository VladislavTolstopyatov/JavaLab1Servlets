package services;

import dao.UserDao;
import dto.CreateUserDto;
import dto.UserDto;
import entities.User;
import exceptions.LoginAlreadyRegisteredException;
import mappers.CreateUserMapper;
import mappers.UserDtoMapper;
import mappers.UserMapper;

import java.util.List;

public class UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final CreateUserMapper createUserMapper;
    private final UserDtoMapper userDtoMapper;

    public UserService(UserDao userDao, UserMapper userMapper, CreateUserMapper createUserMapper, UserDtoMapper userDtoMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.createUserMapper = createUserMapper;
        this.userDtoMapper = userDtoMapper;
    }

    public List<UserDto> findAll() {
        return userDao.findAll().stream().map(userMapper::map).toList();
    }

    public boolean deleteById(Integer id) {
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
        return userDao.save(createUserMapper.map(userDto));
    }

    public void updateUser(UserDto userDto) {
        userDao.update(userDtoMapper.map(userDto));
    }

    public UserDto findByPassword(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("password is empty!");
        }
        return userMapper.map(userDao.findByPassword((password)));
    }

    public UserDto findById(Integer id) {
        return userMapper.map(userDao.findById(id));
    }
}
