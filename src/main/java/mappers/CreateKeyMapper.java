package mappers;

import dao.GameDao;
import dao.KeyDao;
import dto.KeyDto;
import entities.Key;
import exceptions.DataBaseException;
import services.GameService;
import services.KeyService;

public class CreateKeyMapper implements Imapper<KeyDto, Key> {
    private GameService gameService = new GameService(new GameDao(),
            new KeyDao(),
            new GameMapper(),
            new CreateGameMapper(),
            new UpdateGameMapper());

    @Override
    public Key map(KeyDto keyDto) throws DataBaseException {
        return new Key(null,
                keyDto.getKeyStr(),
                gameService.findIdByTitle(keyDto.getGameTitle()));
    }
}
