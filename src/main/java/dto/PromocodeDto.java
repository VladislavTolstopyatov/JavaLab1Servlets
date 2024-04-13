package dto;

public class PromocodeDto {
    private String promoStr;
    private double discount;

    public PromocodeDto(String promoStr, double discount) {
        this.promoStr = promoStr;
        this.discount = discount;
    }

    public String getPromoStr() {
        return promoStr;
    }

    public void setPromoStr(String promoStr) {
        this.promoStr = promoStr;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
