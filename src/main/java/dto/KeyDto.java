package dto;

public class KeyDto {
    private Integer id;
    private String keyStr;

    private String gameTitle;

    public KeyDto(Integer id, String keyStr, String gameTitle) {
        this.id = id;
        this.keyStr = keyStr;
        this.gameTitle = gameTitle;
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

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }
}
