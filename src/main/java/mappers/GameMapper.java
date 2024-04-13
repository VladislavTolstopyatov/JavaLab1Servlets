package mappers;

import dto.GameDto;
import entities.Game;

public class GameMapper implements Imapper<Game, GameDto> {
    @Override
    public GameDto map(Game game) {
        return new GameDto(game.getTitle(),
                game.getDescription(),
                game.getPrice(),
                game.getDateOfRelease(),
                null,
                null);
    }
}
