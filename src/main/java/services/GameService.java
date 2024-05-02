package services;

import dao.GameDao;
import dao.KeyDao;
import dto.GameDto;
import dto.KeyDto;
import entities.Game;
import entities.Key;
import exceptions.DataBaseException;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import mappers.CreateGameMapper;
import mappers.GameMapper;
import mappers.KeyMapper;
import mappers.UpdateGameMapper;

import java.util.List;

public class GameService {
    private final GameDao gameDao;
    private final KeyDao keyDao;
    private final GameMapper gameMapper;
    private final CreateGameMapper createGameMapper;
    private final UpdateGameMapper updateGameMapper;

    public GameService(GameDao gameDao, KeyDao keyDao, GameMapper gameMapper, CreateGameMapper createGameMapper, UpdateGameMapper updateGameMapper) {
        this.gameDao = gameDao;
        this.keyDao = keyDao;
        this.gameMapper = gameMapper;
        this.createGameMapper = createGameMapper;
        this.updateGameMapper = updateGameMapper;
    }

    public GameDto findById(Integer id) throws DataBaseException {
        return gameMapper.map(gameDao.findById(id));
    }

    public GameDto findByTitle(String title) throws DataBaseException {
        Game game = gameDao.findByTitle(title);
        if (game != null) {
            List<Key> keys = keyDao.findAllByTitle(title);
            GameDto gameDto = gameMapper.map(game);
            gameDto.setKeys(keys);
            gameDto.setKeysCount(keys.size());
            return gameDto;
        }
        return null;
    }

    public Game createGame(GameDto gameDto) throws GameWithSuchTitleAlreadyExistsException, DataBaseException {
        Game game;
        game = gameDao.save(createGameMapper.map(gameDto));
        return game;
    }

    public List<GameDto> getAll() throws DataBaseException {
        List<Game> games = gameDao.findAll();
        List<GameDto> gamesDto = games.stream().map(gameMapper::map).toList();
        for (GameDto g : gamesDto) {
            g.setKeys(keyDao.findAllByTitle(g.getTitle()));
            g.setKeysCount(g.getKeys().size());
        }
        return gamesDto;
    }

    public String findTitleById(Integer id) {
        return gameDao.findTitleById(id);
    }

    public Integer findIdByTitle(String title) throws DataBaseException {
        return gameDao.findIdByTitle(title);
    }

    public void update(GameDto gameDto) throws DataBaseException {
        gameDao.update(updateGameMapper.map(gameDto));
    }

    public boolean deleteById(Integer id) {
        return gameDao.deleteById(id);
    }
}
