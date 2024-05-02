package mappers;

import dao.GameDao;
import dao.KeyDao;
import dto.KeyDto;
import entities.Key;
import exceptions.DataBaseException;
import services.GameService;

public class KeyMapper implements Imapper<Key, KeyDto> {
    private GameService gameService = new GameService(new GameDao(),
            new KeyDao(), new GameMapper(), new CreateGameMapper(), new UpdateGameMapper());

    @Override
    public KeyDto map(Key key) {
        return new KeyDto(key.getId(),
                key.getKeyStr(),
                gameService.findTitleById(key.getGame_id()));

    }
}
