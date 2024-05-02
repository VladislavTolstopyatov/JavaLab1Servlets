package services;

import dao.KeyDao;
import dto.KeyDto;
import entities.Key;
import exceptions.DataBaseException;
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

    public KeyDto findById(Integer id) throws DataBaseException {
        return keyMapper.map(keyDao.findById(id));
    }

    public List<KeyDto> findByGameId(Integer id) throws DataBaseException {
        return keyDao.findAllByGameId(id).stream().map(keyMapper::map).toList();
    }

    public boolean deleteById(Integer id) throws DataBaseException {
        return keyDao.deleteById(id);
    }

    public void update(Key key) throws DataBaseException {
        keyDao.update(key);
    }

    public Key createKey(KeyDto keyDto) throws DataBaseException {
        Key key = createKeyMapper.map(keyDto);
        return keyDao.save(key);
    }

    public List<KeyDto> getAll() throws DataBaseException {
        return keyDao.findAll().stream().map(keyMapper::map).toList();
    }

    public List<KeyDto> getAllByTitle(String title) throws DataBaseException {
        return keyDao.findAllByTitle(title).stream().map(keyMapper::map).toList();
    }
}
