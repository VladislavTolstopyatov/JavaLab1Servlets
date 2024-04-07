package entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Purchase {
    private Integer id;
    private LocalDate dateOfPurchase;
    private Integer promocode_id;
    private Integer userId;
    private Integer gameId;

    public Purchase(){}

    public Purchase(Integer id, LocalDate dateOfPurchase, Integer promocode_id, Integer gameId, Integer userId) {
        this.id = id;
        this.dateOfPurchase = dateOfPurchase;
        this.promocode_id = promocode_id;
        this.gameId = gameId;
        this.userId = userId;
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

    public Integer getPromocode_id() {
        return promocode_id;
    }

    public void setPromocode_id(Integer promocode) {
        this.promocode_id = promocode_id;
    }

    public Integer getGameId() {
        return gameId;
    }

    public void setGameId(Integer gameId) {
        this.gameId = gameId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Purchase purchase = (Purchase) o;
        return Objects.equals(id, purchase.id) && Objects.equals(dateOfPurchase, purchase.dateOfPurchase) && Objects.equals(promocode_id, purchase.promocode_id) && Objects.equals(userId, purchase.userId) && Objects.equals(gameId, purchase.gameId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateOfPurchase, promocode_id, userId, gameId);
    }
}
