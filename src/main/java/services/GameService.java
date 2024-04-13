package services;

import dao.GameDao;
import dao.KeyDao;
import dto.GameDto;
import entities.Game;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import mappers.GameMapper;

import java.util.List;

public class GameService {
    private final GameDao gameDao = new GameDao();
    private final KeyDao keyDao = new KeyDao();
    private final GameMapper gameMapper = new GameMapper();

    public GameDto findById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id < 0!");
        }
        return gameMapper.map(gameDao.findById(id));
    }

    public GameDto findByTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("title is empty!");
        }
        return gameMapper.map(gameDao.findByTitle(title));
    }

    public Game createGame(GameDto gameDto) {
        Game game;
        try {
            game = gameDao.save(new Game(null, gameDto.getTitle(),
                    gameDto.getDescription(),
                    gameDto.getPrice(),
                    gameDto.getDateOfRelease(), null));
        } catch (GameWithSuchTitleAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
        return game;
    }

    public List<GameDto> getAll() {
        List<Game> games = gameDao.findAll();
        List<GameDto> gamesDto = games.stream().map(gameMapper::map).toList();
        for (GameDto g : gamesDto) {
            g.setKeys(keyDao.findAllByTitle(g.getTitle()));
            g.setKeysCount(g.getKeys().size());
        }
        return gamesDto;
    }

    public String findTitleById(Integer id) {
        if (id <=0) {
            throw new IllegalArgumentException("id<=0");
        }
        return gameDao.FindTitleById(id);
    }

    public Integer findIdByTitle(String title) {
        if (title.isEmpty()) {
            throw new IllegalArgumentException("title is empty");
        }
        return gameDao.FindIdByTitle(title);
    }

    public void update(Game game) {
        gameDao.update(game);
    }

    public boolean delete(Integer id) {
        return gameDao.deleteById(id);
    }
}
