package services;

import dao.KeyDao;
import dto.KeyDto;
import entities.Game;
import entities.Key;
import entities.Promocode;
import mappers.KeyMapper;

import java.util.List;

public class KeyService {
    private final KeyDao keyDao = new KeyDao();
    private final KeyMapper keyMapper = new KeyMapper();

    public KeyDto findById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id < 0!");
        }
        return keyMapper.map(keyDao.findById(id));
    }

    public List<KeyDto> findByGameId(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id < 0!");
        }
        return keyDao.findAllByGameId(id).stream().map(keyMapper::map).toList();
    }

    public boolean deleteById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("Id < 0!");
        }
        return keyDao.deleteById(id);
    }

    public void update(Key key) {
        keyDao.update(key);
    }

    public Key createKey(Key key) {
        return keyDao.save(new Key(null, key.getKeyStr(), key.getGame_id()));
    }
}
