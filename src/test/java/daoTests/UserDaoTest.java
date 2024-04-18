package daoTests;

import dao.UserDao;
import entities.Role;
import entities.User;
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
        User user = new User(null, "testLogin","password", 50, "88005553535", Role.USER, null);

        User userFromDao = userDao.save(user);

        user.setId(userFromDao.getId());

        List<User> users = userDao.findAll();
        if (users.isEmpty()) {
            fail();
        }
        userFromDao = users.get(0);
        assertEquals(user, userFromDao);
        userDao.deleteById(userFromDao.getId());
    }

    @Test
    void findByIdUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "login", "password", 0, "21551251", Role.USER, null));
        User findUser = userDao.findById(user.getId());
        assertEquals(user, findUser);
        userDao.deleteById(findUser.getId());
    }

    @Test
    void findAllUsersTest() throws LoginAlreadyRegisteredException {
        User user1 = new User(null, "log1", "newpar1", 5, "123", Role.USER, null);
        User user2 = new User(null, "log2", "newpar2", 5, "123", Role.USER, null);


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

        for (User user : users) {
            userDao.deleteById(user.getId());
        }
    }

    @Test
    void updateUserTest() throws LoginAlreadyRegisteredException {
        User userToInsert = new User(null, "log", "paww", 5, "151521", Role.USER, null);
        User savedUser = userDao.save(userToInsert);
        userToInsert.setId(savedUser.getId());

        userToInsert.setBalance(500);

        userDao.update(userToInsert);
        savedUser = userDao.findById(userToInsert.getId());

        assertEquals(savedUser, userToInsert);
        userDao.deleteById(userToInsert.getId());
    }

    @Test
    void findByLoginUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "l", "hji", 0, "51512", Role.USER, null));
        User userCheck = userDao.findByLogin("l");
        assertEquals(user, userCheck);
        userDao.deleteById(userCheck.getId());
    }

    @Test
    void findByPasswordUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "logindel2", "hji", 0, "51512", Role.USER, null));
        User userCheck = userDao.findByPassword("hji");
        assertEquals(user, userCheck);
        userDao.deleteById(userCheck.getId());
    }

    @Test
    void findByLoginAndPasswordUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "logindel3", "PASS", 0, "51512", Role.USER, null));
        User userCheck = userDao.findByLoginAndPassword("logindel3", "PASS");
        assertEquals(user, userCheck);
        userDao.deleteById(userCheck.getId());
    }

    @Test
    void deleteByIdUserTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "logindel1", "pass", 0, "51512", Role.USER, null));
        userDao.deleteById(user.getId());
        assertNull(userDao.findById(user.getId()));
    }

    @Test
    void DeleteByLoginTest() throws LoginAlreadyRegisteredException {
        User user = userDao.save(new User(null, "logindel", "hji", 0, "51512", Role.USER, null));
        userDao.deleteByLogin(user.getLogin());
        assertNull(userDao.findByLogin(user.getLogin()));
    }
}
