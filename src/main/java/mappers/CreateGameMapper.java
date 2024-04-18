package mappers;

import dto.GameDto;
import entities.Game;

public class CreateGameMapper implements Imapper<GameDto, Game> {
    @Override
    public Game map(GameDto gameDto) {
        return new Game(gameDto.getId(),
                gameDto.getTitle(),
                gameDto.getDescription(),
                gameDto.getPrice(),
                gameDto.getDateOfRelease(),
                gameDto.getKeys());
    }
}
