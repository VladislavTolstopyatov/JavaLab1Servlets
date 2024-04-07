package daoTests;

import dao.GameDao;
import entity.Game;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GameDaoTest {
    GameDao gameDao = new GameDao();


    @Test
    void findAllGamesTest() throws GameWithSuchTitleAlreadyExistsException {
        Game game1 = new Game(null, "a", "a", 50, LocalDate.now());
        Game game2 = new Game(null, "b", "b", 30, LocalDate.now());

        List<Game> games = new ArrayList<>();
        games.add(game1);
        games.add(game2);

        gameDao.save(game1);
        gameDao.save(game2);

        List<Game> gamesFromDao = gameDao.findAll();
        for (int i = 0; i < gamesFromDao.size(); i++) {
            games.get(i).setId(gamesFromDao.get(i).getId());
        }

        assertTrue(games.size() == gamesFromDao.size() &&
                gamesFromDao.containsAll(games) && games.containsAll(gamesFromDao));
    }

    @Test
    void saveGameTest() throws GameWithSuchTitleAlreadyExistsException {
        Game testGame = new Game(null, "testTitle", "testDescription", 1000, LocalDate.now());
        gameDao.save(testGame);
        List<Game> games = gameDao.findAll();
        if (games.isEmpty()) {
            fail();
        }
        Game checkGame = games.get(0);
        testGame.setId(checkGame.getId());
        assertEquals(testGame, checkGame);
    }

    @Test
    void findByIdGameTest() throws GameWithSuchTitleAlreadyExistsException {
        Game game = gameDao.save(new Game(null, "test", "testDescription", 1000, LocalDate.now()));
        Game gameFromDao = gameDao.findById(game.getId());
        assertEquals(game, gameFromDao);
    }

    @Test
    void updateGameTest() throws GameWithSuchTitleAlreadyExistsException {
        Game gameToInsert = new Game(null, "testing", "testing", 25, LocalDate.now());
        Game game = gameDao.save(gameToInsert);

        gameToInsert.setId(game.getId());
        gameToInsert.setPrice(10);
        gameDao.update(gameToInsert);

        Game result = gameDao.findById(game.getId());
        assertEquals(result, gameToInsert);
    }

    @Test
    void deleteByIdGameTest() throws GameWithSuchTitleAlreadyExistsException {
        Game gameToInsert = new Game(null, "K", "K", 2000, LocalDate.now());
        Game gameFromDao = gameDao.save(gameToInsert);

        gameToInsert.setId(gameFromDao.getId());
        gameDao.deleteById(gameToInsert.getId());

        assertNull(gameDao.findById(gameToInsert.getId()));
    }

    @Test
    void findByTitleGameTest() throws GameWithSuchTitleAlreadyExistsException {
        Game gameToInsert = new Game(null, "tTITLE", "ababab", 2000, LocalDate.now());
        Game gameFromDao = gameDao.save(gameToInsert);

        Game findGameByTitle = gameDao.findByTitle(gameToInsert.getTitle());

        assertEquals(gameToInsert.getTitle(), gameFromDao.getTitle());
    }
}
