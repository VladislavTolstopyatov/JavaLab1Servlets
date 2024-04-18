package services;

import dao.UserDao;
import dto.CreateUserDto;
import dto.UserDto;
import entities.User;
import exceptions.LoginAlreadyRegisteredException;
import mappers.CreateUserMapper;
import mappers.UserMapper;

import java.util.List;
import java.util.Objects;

public class UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final CreateUserMapper createUserMapper;

    public UserService(UserDao userDao, UserMapper userMapper, CreateUserMapper createUserMapper) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.createUserMapper = createUserMapper;
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

    public void updateUser(User user) {
        userDao.update(user);
    }

    public UserDto findByPassword(String password) {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("password is empty!");
        }
        return userMapper.map(userDao.findByPassword((password)));
    }
}
