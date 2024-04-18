package mappers;

import dao.GameDao;
import dao.KeyDao;
import dto.GameDto;
import entities.Game;
import services.GameService;

public class UpdateGameMapper implements Imapper<GameDto, Game> {
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
