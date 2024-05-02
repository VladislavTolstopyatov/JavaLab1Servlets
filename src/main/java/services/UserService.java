package services;

import dao.UserDao;
import dto.CreateUserDto;
import dto.UserDto;
import entities.User;
import exceptions.DataBaseException;
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

    public List<UserDto> findAll() throws DataBaseException {
        return userDao.findAll().stream().map(userMapper::map).toList();
    }

    public boolean deleteById(Integer id) throws DataBaseException {
        return userDao.deleteById(id);
    }

    public UserDto findByLogin(String login) throws DataBaseException {
        return userMapper.map(userDao.findByLogin(login));
    }

    public UserDto findByLoginAndPassword(String login, String password) throws DataBaseException, Exception {
        if (login.isEmpty() || password.isEmpty()) {
            return null;
        }
        User user = userDao.findByLoginAndPassword(login, password);
        if (user == null) {
            throw new Exception("user not found");
        }
        return userMapper.map(user);
    }

    public boolean deleteByLogin(String login) throws DataBaseException {
        if (login.isEmpty()) {
            throw new IllegalArgumentException("login is empty");
        }
        return userDao.deleteByLogin(login);
    }

    public User createUser(CreateUserDto userDto) throws LoginAlreadyRegisteredException, DataBaseException {
        return userDao.save(createUserMapper.map(userDto));
    }

    public void updateUser(UserDto userDto) throws DataBaseException {
        userDao.update(userDtoMapper.map(userDto));
    }

    public UserDto findByPassword(String password) throws DataBaseException {
        if (password.isEmpty()) {
            throw new IllegalArgumentException("password is empty!");
        }
        return userMapper.map(userDao.findByPassword((password)));
    }

    public UserDto findById(Integer id) throws DataBaseException {
        return userMapper.map(userDao.findById(id));
    }
}
