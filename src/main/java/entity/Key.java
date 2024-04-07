package entity;

import java.util.Objects;

public class Key {
    private Integer id;
    private String keyStr;
    private Integer game_id;

public Key(Integer id, String keyStr, Integer game_id) {
        this.id = id;
        this.keyStr = keyStr;
        this.game_id = game_id;
    }

    public Key() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public Integer getGame_id() {
        return game_id;
    }

    public void setGame_id(Integer game_id) {
        this.game_id = game_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Key key = (Key) o;
        return Objects.equals(id, key.id) && Objects.equals(keyStr, key.keyStr) && Objects.equals(game_id, key.game_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyStr, game_id);
    }
}
