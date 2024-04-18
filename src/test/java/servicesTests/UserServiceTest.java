package servicesTests;

import dao.UserDao;
import dto.CreateUserDto;
import dto.UserDto;
import entities.Role;
import entities.User;
import exceptions.LoginAlreadyRegisteredException;
import mappers.CreateUserMapper;
import mappers.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.UserService;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    @Mock
    private UserDao userDao;
    @Mock
    private UserMapper userMapper;
    @Mock
    private CreateUserMapper createUserMapper;
    @InjectMocks
    private UserService userService;

    @Test
    void testCreateUserWhenLoginNotExists() throws LoginAlreadyRegisteredException {
        CreateUserDto createUserDto = getCreateUserDto();
        User user = getUser();

        when(createUserMapper.map(createUserDto)).thenReturn(user);
        when(userDao.save(user)).thenReturn(user);

        User actualResult = userService.createUser(createUserDto);

        assertThat(actualResult).isEqualTo(user);
    }

    @Test
    void testCreateUserWhenLoginExists() throws LoginAlreadyRegisteredException {
        CreateUserDto createUserDto = getCreateUserDto();
        User user = getUser();

        when(createUserMapper.map(createUserDto)).thenReturn(user);
        when(userDao.save(user)).thenThrow(LoginAlreadyRegisteredException.class);

        LoginAlreadyRegisteredException exception = assertThrows(LoginAlreadyRegisteredException.class,
                () -> userService.createUser(createUserDto));
    }

    @Test
    void deleteByIdWhenUserNotFound() throws LoginAlreadyRegisteredException {
        Integer id = 1;
        when(userDao.deleteById(id)).thenReturn(false);
        assertThat(userService.deleteById(id)).isEqualTo(false);
    }

    @Test
    void deleteByIdWhenUserFound() throws LoginAlreadyRegisteredException {
        Integer id = 1;
        when(userDao.deleteById(id)).thenReturn(true);
        assertThat(userService.deleteById(id)).isEqualTo(true);
    }

    @Test
    void findByLoginWhenUserExists() throws LoginAlreadyRegisteredException {
        UserDto userDto = getUserDto();
        User user = getUser();
        String login = user.getLogin();
        when(userMapper.map(user)).thenReturn(userDto);
        when(userDao.findByLogin(login)).thenReturn(user);

        assertThat(userService.findByLogin(login)).isEqualTo(userDto);
    }

    @Test
    void findByLoginWhenUserNotExists() throws LoginAlreadyRegisteredException {
        User user = getUser();
        String login = user.getLogin();
        when(userDao.findByLogin(login)).thenReturn(null);
        assertThat(userService.findByLogin(login)).isEqualTo(null);
    }

    @Test
    void findByLoginAndPasswordWhenUserExists() throws LoginAlreadyRegisteredException {
        UserDto userDto = getUserDto();
        User user = getUser();
        String login = user.getLogin();
        String password = user.getPassword();

        when(userMapper.map(user)).thenReturn(userDto);
        when(userDao.findByLoginAndPassword(login, password)).thenReturn(user);

        assertThat(userService.findByLoginAndPassword(login, password)).isEqualTo(userDto);
    }

    @Test
    void findByLoginAndPasswordWhenUserNotExists() throws LoginAlreadyRegisteredException {
        User user = getUser();
        String login = user.getLogin();
        String password = user.getPassword();
        when(userDao.findByLoginAndPassword(login, password)).thenReturn(null);
        assertThat(userService.findByLoginAndPassword(login, password)).isEqualTo(null);
    }

    @Test
    void deleteByLoginWhenUserNotFound() throws LoginAlreadyRegisteredException {
        String login = "login";
        when(userDao.deleteByLogin(login)).thenReturn(false);
        assertThat(userService.deleteByLogin(login)).isEqualTo(false);
    }

    @Test
    void deleteByLoginWhenUserFound() throws LoginAlreadyRegisteredException {
        String login = "login";
        when(userDao.deleteByLogin(login)).thenReturn(true);
        assertThat(userService.deleteByLogin(login)).isEqualTo(true);
    }

    @Test
    void findByPasswordWhenUserFound() throws LoginAlreadyRegisteredException {
        UserDto userDto = getUserDto();
        User user = getUser();
        String password = user.getPassword();

        when(userDao.findByPassword(password)).thenReturn(user);
        when(userMapper.map(user)).thenReturn(userDto);

        assertThat(userService.findByPassword(password)).isEqualTo(userDto);
    }

    @Test
    void findByLoginAndPasswordWhenUserNotFound() throws LoginAlreadyRegisteredException {
        User user = getUser();
        String password = user.getPassword();
        when(userDao.findByPassword(password)).thenReturn(null);
        assertThat(userService.findByPassword(password)).isEqualTo(null);
    }

    @Test
    void findAllWhenUsersNotEmpty() {
        User user = getUser();
        List<User> users = List.of(user);
        UserDto userDto = getUserDto();

        when(userDao.findAll())
                .thenReturn(users);
        when(userMapper.map(user))
                .thenReturn(userDto);


        assertThat(userService.findAll())
                .hasSize(1);
        userService.findAll().stream().map(UserDto::getId)
                .forEach(id -> assertThat(id).isEqualTo(1));
    }

    @Test
    void findAllWhenUsersEmpty() {
        when(userDao.findAll())
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> userService.findAll());
    }
    private static UserDto getUserDto() {
        return new UserDto(1,
                "test",
                10000,
                "test",
                Role.USER,
                null);
    }


    private static User getUser() {
        return new User(1,
                "test",
                "password",
                10000,
                "test",
                Role.USER,
                Collections.emptyList());

    }

    private static CreateUserDto getCreateUserDto() {
        return new CreateUserDto("test",
                "password1",
                10000,
                "test",
                Role.USER);
    }
}
