package services;

import dao.KeyDao;
import dto.KeyDto;
import entities.Game;
import entities.Key;
import entities.Promocode;
import mappers.KeyMapper;

import java.util.List;

public class KeyService {
    private final KeyDao keyDao;
    private final KeyMapper keyMapper;

    public KeyService(KeyDao keyDao, KeyMapper keyMapper) {
        this.keyDao = keyDao;
        this.keyMapper = keyMapper;
    }

    public KeyDto findById(Integer id) {
        return keyMapper.map(keyDao.findById(id));
    }

    public List<KeyDto> findByGameId(Integer id) {
        return keyDao.findAllByGameId(id).stream().map(keyMapper::map).toList();
    }

    public boolean deleteById(Integer id) {
        return keyDao.deleteById(id);
    }

    public void update(Key key) {
        keyDao.update(key);
    }

    public Key createKey(Key key) {
        return keyDao.save(key);
    }

    public List<KeyDto> getAll() {
        return keyDao.findAll().stream().map(keyMapper::map).toList();
    }

    public List<KeyDto> getAllByTitle(String title) {
        return keyDao.findAllByTitle(title).stream().map(keyMapper::map).toList();
    }
}
