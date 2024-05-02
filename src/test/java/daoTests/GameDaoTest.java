package daoTests;

import dao.GameDao;
import entities.Game;
import exceptions.DataBaseException;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameDaoTest {
    GameDao gameDao = new GameDao();


    @Test
    void findAllGamesTest() throws GameWithSuchTitleAlreadyExistsException, DataBaseException {
        Game game1 = new Game(null, "a", "a", 50, LocalDate.now(), null);
        Game game2 = new Game(null, "b", "b", 30, LocalDate.now(), null);

        List<Game> games = new ArrayList<>();
        games.add(game1);
        games.add(game2);

        gameDao.save(game1);
        gameDao.save(game2);

        List<Game> gamesFromDao = gameDao.findAll();

        games.get(0).setId(gamesFromDao.get(gamesFromDao.size() - 2).getId());
        games.get(1).setId(gamesFromDao.get(gamesFromDao.size() - 1).getId());

        assertTrue(games.get(0).equals(gamesFromDao.get(gamesFromDao.size() - 2)) &&
                games.get(1).equals(gamesFromDao.get(gamesFromDao.size() - 1)));

        for (Game game : games) {
            gameDao.deleteById(game.getId());
        }
    }

    @Test
    void saveGameTest() throws GameWithSuchTitleAlreadyExistsException, DataBaseException {
        Game testGame = new Game(null, "testTitle", "testDescription", 1000, LocalDate.now(), null);
        gameDao.save(testGame);
        List<Game> games = gameDao.findAll();
        if (games.isEmpty()) {
            fail();
        }
        Game checkGame = games.get(games.size() - 1);
        testGame.setId(checkGame.getId());
        assertEquals(testGame, checkGame);
        gameDao.deleteById(checkGame.getId());
    }

    @Test
    void findByIdGameTest() throws GameWithSuchTitleAlreadyExistsException, DataBaseException {
        Game game = gameDao.save(new Game(null, "test", "testDescription", 1000, LocalDate.now(), null));
        Game gameFromDao = gameDao.findById(game.getId());
        assertEquals(game, gameFromDao);
        gameDao.deleteById(gameFromDao.getId());
    }

    @Test
    void updateGameTest() throws GameWithSuchTitleAlreadyExistsException, DataBaseException {
        Game gameToInsert = new Game(null, "testing", "testing", 25, LocalDate.now(), null);
        Game game = gameDao.save(gameToInsert);

        gameToInsert.setId(game.getId());
        gameToInsert.setPrice(10);
        gameDao.update(gameToInsert);

        Game result = gameDao.findById(game.getId());
        assertEquals(result, gameToInsert);
        gameDao.deleteById(game.getId());
    }

    @Test
    void deleteByIdGameTest() throws GameWithSuchTitleAlreadyExistsException, DataBaseException {
        Game gameToInsert = new Game(null, "K", "K", 2000, LocalDate.now(), null);
        Game gameFromDao = gameDao.save(gameToInsert);

        gameToInsert.setId(gameFromDao.getId());
        gameDao.deleteById(gameToInsert.getId());

        assertNull(gameDao.findById(gameToInsert.getId()));
    }

    @Test
    void findByTitleGameTest() throws GameWithSuchTitleAlreadyExistsException, DataBaseException {
        Game gameToInsert = new Game(null, "tTITLE", "ababab", 2000, LocalDate.now(), null);
        Game gameFromDao = gameDao.save(gameToInsert);

        Game findGameByTitle = gameDao.findByTitle(gameToInsert.getTitle());

        assertEquals(gameToInsert.getTitle(), gameFromDao.getTitle());
        gameDao.deleteById(findGameByTitle.getId());
    }
}
