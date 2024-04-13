package entities;

import java.time.LocalDate;
import java.util.Objects;

public class Purchase {
    private Integer id;
    private LocalDate dateOfPurchase;
    private Integer promocodeId;
    private Integer userId;
    private Integer gameId;
    private String keyStr;

    public Purchase() {
    }

    public Purchase(Integer id, LocalDate dateOfPurchase, Integer promocodeId, Integer userId, Integer gameId, String keyStr) {
        this.id = id;
        this.dateOfPurchase = dateOfPurchase;
        this.promocodeId = promocodeId;
        this.userId = userId;
        this.gameId = gameId;
        this.keyStr = keyStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Integer getPromocodeId() {
        return promocodeId;
    }

    public void setPromocodeId(Integer promocodeId) {
        this.promocodeId = promocodeId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id) &&
                Objects.equals(dateOfPurchase, purchase.dateOfPurchase) &&
                Objects.equals(promocodeId, purchase.promocodeId) &&
                Objects.equals(userId, purchase.userId) &&
                Objects.equals(gameId, purchase.gameId) &&
                Objects.equals(keyStr, purchase.keyStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfPurchase, promocodeId, userId, gameId, keyStr);
    }
}
