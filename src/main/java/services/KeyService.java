package services;

import dao.KeyDao;
import dto.KeyDto;
import entities.Key;
import mappers.CreateKeyMapper;
import mappers.KeyMapper;

import java.util.List;

public class KeyService {
    private final KeyDao keyDao;
    private final KeyMapper keyMapper;

    private final CreateKeyMapper createKeyMapper;

    public KeyService(KeyDao keyDao, KeyMapper keyMapper, CreateKeyMapper createKeyMapper) {
        this.keyDao = keyDao;
        this.keyMapper = keyMapper;
        this.createKeyMapper = createKeyMapper;
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

    public Key createKey(KeyDto keyDto) {
        Key key = createKeyMapper.map(keyDto);
        return keyDao.save(key);
    }

    public List<KeyDto> getAll() {
        return keyDao.findAll().stream().map(keyMapper::map).toList();
    }

    public List<KeyDto> getAllByTitle(String title) {
        return keyDao.findAllByTitle(title).stream().map(keyMapper::map).toList();
    }
}
