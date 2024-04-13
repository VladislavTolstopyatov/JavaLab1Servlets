package dto;

import entities.Game;

import java.time.LocalDate;

public class PurchaseDto {
    private LocalDate dateOfPurchase;
    private Integer promocodeid;
    private String gameTitle;
    private Integer userId;
    private String keyStr;

    public PurchaseDto(LocalDate dateOfPurchase, Integer promocodeid, String gameTitle, Integer userId, String keyStr) {
        this.dateOfPurchase = dateOfPurchase;
        this.promocodeid = promocodeid;
        this.gameTitle = gameTitle;
        this.userId = userId;
        this.keyStr = keyStr;
    }

    public LocalDate getDateOfPurchase() {
        return dateOfPurchase;
    }

    public void setDateOfPurchase(LocalDate dateOfPurchase) {
        this.dateOfPurchase = dateOfPurchase;
    }

    public Integer getPromocodeid() {
        return promocodeid;
    }

    public void setPromocodeid(Integer promocodeid) {
        this.promocodeid = promocodeid;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public void setGameTitle(String gameTitle) {
        this.gameTitle = gameTitle;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }
}
