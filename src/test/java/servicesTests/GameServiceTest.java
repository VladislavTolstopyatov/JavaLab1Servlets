package servicesTests;

import dao.GameDao;
import dao.KeyDao;
import dto.GameDto;
import entities.Game;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import mappers.CreateGameMapper;
import mappers.GameMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.GameService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    GameDao gameDao;
    @Mock
    KeyDao keyDao;
    @Mock
    GameMapper gameMapper;

    @Mock
    CreateGameMapper createGameMapper;
    @InjectMocks
    GameService gameService;

    @Test
    void deleteByIdWhenGameExists() {
        Integer id = 1;
        when(gameDao.deleteById(id)).thenReturn(true);
        assertThat(gameService.deleteById(id)).isEqualTo(true);
    }

    @Test
    void deleteByIdWhenGameNotExists() {
        Integer id = 1;
        when(gameDao.deleteById(id)).thenReturn(false);
        assertThat(gameService.deleteById(id)).isEqualTo(false);
    }

    @Test
    void findByTitleWhenGameExists() throws GameWithSuchTitleAlreadyExistsException {
        GameDto gameDto = getGameDto();
        Game game = getGame();
        String title = gameDto.getTitle();

        when(gameDao.findByTitle(title)).thenReturn(game);
        when(gameMapper.map(game)).thenReturn(gameDto);

        assertThat(gameService.findByTitle(title)).isEqualTo(gameDto);
    }

    @Test
    void findByTitleWhenGameNotExists() throws GameWithSuchTitleAlreadyExistsException {
        GameDto gameDto = getGameDto();
        String title = gameDto.getTitle();

        when(gameDao.findByTitle(title)).thenReturn(null);
        assertThat(gameService.findByTitle(title)).isEqualTo(null);
    }

    @Test
    void getAllWhenGamesNotEmpty() {
        Game game = getGame();
        List<Game> games = List.of(game);
        GameDto gameDto = getGameDto();

        when(gameDao.findAll())
                .thenReturn(games);
        when(gameMapper.map(game))
                .thenReturn(gameDto);

        assertThat(gameService.getAll())
                .hasSize(1);
        assertThat(gameService.getAll().get(0).getId()).
                isEqualTo(gameDto.getId());
    }

    @Test
    void getAllWhenGamesEmpty() {
        when(gameDao.findAll())
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> gameService.getAll());
    }

    @Test
    void findByIdWhenGameExists() {
        Game game = getGame();
        GameDto gameDto = getGameDto();
        Integer id = gameDto.getId();

        when(gameDao.findById(id)).thenReturn(game);
        when(gameMapper.map(game)).thenReturn(gameDto);

        assertThat(gameService.findById(id)).isEqualTo(gameDto);
    }

    @Test
    void findByIdWhenGameNotExists() {
        Integer id = 1;
        when(gameDao.findById(id)).thenReturn(null);
        assertThat(gameService.findById(id)).isEqualTo(null);
    }

    @Test
    void findTitleByIdWhenGameExists() {
        Game game = getGame();
        GameDto gameDto = getGameDto();

        String title = gameDto.getTitle();
        Integer id = gameDto.getId();

        when(gameDao.findTitleById(id)).thenReturn(title);

        assertThat(gameService.findTitleById(id)).isEqualTo(title);
    }

    @Test
    void findTitleByIdWhenGameNotExists() {
        Integer id = 1;

        when(gameDao.findTitleById(id)).thenReturn(null);
        assertThat(gameService.findTitleById(id)).isEqualTo(null);
    }

    @Test
    void findIdByTitleWhenGameExists() {
        Game game = getGame();
        GameDto gameDto = getGameDto();

        String title = gameDto.getTitle();
        Integer id = gameDto.getId();

        when(gameDao.findIdByTitle(title)).thenReturn(id);

        assertThat(gameService.findIdByTitle(title)).isEqualTo(id);
    }

    @Test
    void findIdByTitleWhenGameNotExists() {
        String title = "title";
        when(gameDao.findIdByTitle(title)).thenReturn(null);
        assertThat(gameService.findIdByTitle(title)).isEqualTo(null);
    }

    @Test
    void createGameWhenTitleUnique() throws GameWithSuchTitleAlreadyExistsException {
        Game game = getGame();
        GameDto gameDto = getGameDto();

        when(createGameMapper.map(gameDto)).thenReturn(game);
        when(gameDao.save(game)).thenReturn(game);
        assertThat(gameService.createGame(gameDto)).isEqualTo(game);
    }

    @Test
    void createGameWhenTitleNotUnique() throws GameWithSuchTitleAlreadyExistsException {
        Game game = getGame();
        GameDto gameDto = getGameDto();

        when(createGameMapper.map(gameDto)).thenReturn(game);
        when(gameDao.save(game)).thenThrow(GameWithSuchTitleAlreadyExistsException.class);
        GameWithSuchTitleAlreadyExistsException exception = assertThrows(GameWithSuchTitleAlreadyExistsException.class,
                () -> gameService.createGame(gameDto));
    }

    private static GameDto getGameDto() {
        return new GameDto(1,
                "title",
                "description",
                0,
                LocalDate.of(2003, 3, 3),
                Collections.emptyList(),
                0);
    }

    private static Game getGame() {
        return new Game(1,
                "title",
                "description",
                0,
                LocalDate.of(2003, 3, 3),
                Collections.emptyList());

    }
}
