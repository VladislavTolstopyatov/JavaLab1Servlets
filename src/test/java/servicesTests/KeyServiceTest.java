package servicesTests;

import dao.KeyDao;
import dto.GameDto;
import dto.KeyDto;
import dto.UserDto;
import entities.Game;
import entities.Key;
import entities.User;
import mappers.KeyMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.KeyService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class KeyServiceTest {
    @InjectMocks
    KeyService keyService;
    @Mock
    KeyDao keyDao;
    @Mock
    KeyMapper keyMapper;


    @Test
    void findByIdWhenKeyExists() {
        Key key = getKey();
        KeyDto keyDto = getKeyDto();

        Integer id = keyDto.getId();

        when(keyDao.findById(id)).thenReturn(key);
        when(keyMapper.map(key)).thenReturn(keyDto);

        Assertions.assertThat(keyService.findById(id)).isEqualTo(keyDto);
    }

    @Test
    void findByIdWhenKeyNotExists() {
        Integer id = 1;
        when(keyDao.findById(id)).thenReturn(null);
        assertThat(keyService.findById(id)).isEqualTo(null);
    }

    @Test
    void findAllWhenKeysNotEmpty() {
        Key key = getKey();
        List<Key> keys = List.of(key);
        KeyDto keyDto = getKeyDto();

        when(keyDao.findAll())
                .thenReturn(keys);
        when(keyMapper.map(key))
                .thenReturn(keyDto);


        Assertions.assertThat(keyService.getAll())
                .hasSize(1);
        assertThat(keyDto.getId()).isEqualTo(keyService.getAll().get(0).getId());
    }

    @Test
    void findAllWhenKeysEmpty() {
        when(keyDao.findAll())
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> keyService.getAll());
    }

    @Test
    void findAllByGameIdWhenKeysExists() {
        GameDto gameDto = getGameDto();
        Key key = getKey();
        List<Key> keys = List.of(key);
        KeyDto keyDto = getKeyDto();

        Integer id = gameDto.getId();

        when(keyDao.findAllByGameId(id))
                .thenReturn(keys);
        when(keyMapper.map(key))
                .thenReturn(keyDto);


        Assertions.assertThat(keyService.findByGameId(id)).hasSize(1);
        assertThat(keyDto.getId()).isEqualTo(keyService.findByGameId(id).get(0).getId());
    }

    @Test
    void findAllByGameTitleWhenKeysNotExists() {
        GameDto gameDto = getGameDto();
        Integer id = gameDto.getId();

        when(keyDao.findAllByGameId(id))
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> keyService.findByGameId(id));
    }

    @Test
    void findAllByGameTitleWhenKeysExists() {
        GameDto gameDto = getGameDto();
        Key key = getKey();
        List<Key> keys = List.of(key);
        KeyDto keyDto = getKeyDto();

        String title = gameDto.getTitle();

        when(keyDao.findAllByTitle(title))
                .thenReturn(keys);
        when(keyMapper.map(key))
                .thenReturn(keyDto);


        Assertions.assertThat(keyService.getAllByTitle(title)).hasSize(1);
        assertThat(keyDto.getId()).isEqualTo(keyService.getAllByTitle(title).get(0).getId());
    }

    @Test
    void findAllByGameIdWhenKeysNotExists() {
        GameDto gameDto = getGameDto();
        String title = gameDto.getTitle();

        when(keyDao.findAllByTitle(title))
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> keyService.getAllByTitle(title));
    }

    @Test
    void deleteByIdWhenKeyExists() {
        KeyDto keyDto = getKeyDto();
        Integer id = keyDto.getId();
        when(keyDao.deleteById(id)).thenReturn(true);
        assertThat(keyService.deleteById(id)).isEqualTo(true);
    }

    @Test
    void deleteByIdWhenKeyNotExists() {
        KeyDto keyDto = getKeyDto();
        Integer id = keyDto.getId();
        when(keyDao.deleteById(id)).thenReturn(false);
        assertThat(keyService.deleteById(id)).isEqualTo(false);
    }

    @Test
    void createKey() {
        Key key = getKey();
        KeyDto keyDto = getKeyDto();

        when(keyDao.save(key)).thenReturn(key);

        assertThat(keyService.createKey(key)).isEqualTo(key);
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

    private static Key getKey() {
        return new Key(1,
                "test",
                1);
    }

    private static KeyDto getKeyDto() {
        return new KeyDto(1,
                "test");
    }
}
