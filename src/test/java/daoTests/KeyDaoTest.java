package daoTests;

import dao.GameDao;
import dao.KeyDao;
import entities.Game;
import entities.Key;
import exceptions.DataBaseException;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KeyDaoTest {
    static GameDao gameDao = new GameDao();
    KeyDao keyDao = new KeyDao();

    static Game game;

    @BeforeEach
    void initGame() throws GameWithSuchTitleAlreadyExistsException, DataBaseException {
        game = gameDao.save(new Game(null, "TestGameForGame", "test", 200, LocalDate.now(),null));
    }

    @AfterEach
    void deleteGameFromDB() {
        gameDao.deleteById(game.getId());
    }

    @Test
    void saveKeyTest() throws DataBaseException {
        Key testKey = new Key(null, "123612hbn", game.getId());
        keyDao.save(testKey);
        List<Key> keys = keyDao.findAll();
        if (keys.isEmpty()) {
            fail();
        }
        Key checkKey = keys.get(0);
        testKey.setId(checkKey.getId());
        assertEquals(testKey, checkKey);
        gameDao.deleteById(game.getId());
    }

    @Test
    void findByIdKeyTest() throws DataBaseException {
        Key keyToInsert = new Key(null, "3690o12yh1ki", game.getId());
        Key keyFromDao = keyDao.save(keyToInsert);
        keyToInsert.setId(keyFromDao.getId());

        keyFromDao = keyDao.findById(keyToInsert.getId());
        gameDao.findById(game.getId());

        assertEquals(keyFromDao, keyToInsert);
        gameDao.deleteById(game.getId());
    }

    @Test
    void findAllKeysTest() throws DataBaseException {
        Key key1 = new Key(null, "21581275asgas", game.getId());
        Key key2 = new Key(null, "21qhqnd", game.getId());

        List<Key> keys = new ArrayList<>();
        keys.add(key1);
        keys.add(key2);

        keyDao.save(key1);
        keyDao.save(key2);

        List<Key> keysFromDao = keyDao.findAll();
        for (int i = 0; i < keysFromDao.size(); i++) {
            keys.get(i).setId(keysFromDao.get(i).getId());
        }

        assertTrue(keys.size() == keysFromDao.size() &&
                keysFromDao.containsAll(keys) && keys.containsAll(keysFromDao));
    }

    @Test
    void findAllKeysByGameIdTest() throws DataBaseException {
        Key key1 = new Key(null, "21581275asgas", game.getId());
        Key key2 = new Key(null, "21qhqnd", game.getId());

        List<Key> keys = new ArrayList<>();
        keys.add(key1);
        keys.add(key2);

        keyDao.save(key1);
        keyDao.save(key2);

        List<Key> keysFromDao = keyDao.findAllByGameId(game.getId());
        for (int i = 0; i < keysFromDao.size(); i++) {
            keys.get(i).setId(keysFromDao.get(i).getId());
        }

        assertTrue(keys.size() == keysFromDao.size() &&
                keysFromDao.containsAll(keys) && keys.containsAll(keysFromDao));

        gameDao.deleteById(game.getId());
    }

    @Test
    void updateKeyTest() throws DataBaseException {
        Key keyToInsert = new Key(null, "32lkgjweh12", game.getId());
        Key keyFromDao = keyDao.save(keyToInsert);
        keyToInsert.setId(keyFromDao.getId());

        keyToInsert.setKeyStr("update");
        keyToInsert.setGame_id(game.getId());

        keyDao.update(keyToInsert);
        Key findKey = keyDao.findById(keyToInsert.getId());

        assertEquals(keyToInsert, findKey);

        gameDao.deleteById(game.getId());
    }

    @Test
    void deleteByIdKeyTest() throws DataBaseException {
        Key keyToInsert = new Key(null, "qlogjql121", game.getId());
        Key savedKey = keyDao.save(keyToInsert);

        keyToInsert.setId(savedKey.getId());

        keyDao.deleteById(keyToInsert.getId());
        assertNull(keyDao.findById(keyToInsert.getId()));
        gameDao.deleteById(game.getId());

    }
}
