package daoTests;

import dao.UserDao;
import entity.Role;
import entity.User;
import exceptions.LoginAlreadyRegisteredException;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class UserDaoTest {

    UserDao userDao = new UserDao();

    @Test
    void saveUserTest() throws LoginAlreadyRegisteredException {
        User user = new User(null, "testLogin", Objects.hash("password"), 50, "88005553535", Role.USER, null);

        User userFromDao = userDao.save(user);

        user.setId(userFromDao.getId());

        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            fail();
        }
        userFromDao = users.get(0);

        assertEquals(user, userFromDao);
    }

    @Test
    void findByIdUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "login", Objects.hash("password"), 0, "21551251", Role.USER, null));
        User findUser = userDao.findById(user.getId());
        assertEquals(user, findUser);
    }

    @Test
    void findAllUsersTest() throws LoginAlreadyRegisteredException {
        User user1 = new User(null, "log1", Objects.hash("newpar1"), 5, "123", Role.USER, null);
        User user2 = new User(null, "log2", Objects.hash("newpar2"), 5, "123", Role.USER, null);


        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);

        userDao.save(user1);
        userDao.save(user2);

        List<User> usersFromDao = userDao.findAll();
        for (int i = 0; i < usersFromDao.size(); i++) {
            users.get(i).setId(usersFromDao.get(i).getId());
        }

        assertTrue(users.size() == usersFromDao.size() &&
                usersFromDao.containsAll(users) && users.containsAll(usersFromDao));
    }

    @Test
    void updateUserTest() throws LoginAlreadyRegisteredException {
        User userToInsert = new User(null, "log", Objects.hash("paww"), 5, "151521", Role.USER, null);
        User savedUser = userDao.save(userToInsert);
        userToInsert.setId(savedUser.getId());

        userToInsert.setBalance(500);

        userDao.update(userToInsert);
        savedUser = userDao.findById(userToInsert.getId());

        assertEquals(savedUser, userToInsert);
    }

    @Test
    void findByLoginUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "l", Objects.hash("hji"), 0, "51512", Role.USER, null));
        User userCheck = userDao.findByLogin("l");
        assertEquals(user, userCheck);
    }

    @Test
    void findByPasswordUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "logindel2", Objects.hash("hji"), 0, "51512", Role.USER, null));
        User userCheck = userDao.findByPassword(Objects.hash("hji"));
        assertEquals(user, userCheck);
    }

    @Test
    void findByLoginAndPasswordUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "logindel3", Objects.hash("hji"), 0, "51512", Role.USER, null));
        User userCheck = userDao.findByLoginAndPassword("logindel3", Objects.hash("hji"));
        assertEquals(user, userCheck);
    }

    @Test
    void deleteByIdUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "logindel1", Objects.hash("hji"), 0, "51512", Role.USER, null));
        userDao.deleteById(user.getId());
        assertNull(userDao.findById(user.getId()));
    }

    @Test
    void DeleteByLoginTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "logindel", Objects.hash("hji"), 0, "51512", Role.USER, null));
        userDao.deleteByLogin(user.getLogin());
        assertNull(userDao.findByLogin(user.getLogin()));

    }
}
