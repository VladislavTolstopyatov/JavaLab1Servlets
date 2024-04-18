package dto;

public class KeyDto {
    private Integer id;
    private String keyStr;

    public KeyDto(Integer id, String keyStr) {
        this.id = id;
        this.keyStr = keyStr;
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
}
