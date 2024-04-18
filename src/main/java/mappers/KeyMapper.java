package mappers;

import dto.KeyDto;
import entities.Key;

public class KeyMapper implements Imapper<Key, KeyDto> {
    @Override
    public KeyDto map(Key key) {
        return new KeyDto(key.getId(),
                key.getKeyStr());
    }
}
