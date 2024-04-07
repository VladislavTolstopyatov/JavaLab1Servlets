package entity;

import java.util.Objects;

public class Promocode {
    private Integer id;
    private String promoStr;
    private double discount;

    public Promocode(Integer id, String promoStr, double discount) {
        this.id = id;
        this.promoStr = promoStr;
        this.discount = discount;
    }

    public Promocode() {
    }

    public Integer getId() {
        return id;
    }

    public String getPromoStr() {
        return promoStr;
    }

    public double getDiscount() {
        return discount;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPromoStr(String promoStr) {
        this.promoStr = promoStr;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Promocode promocode = (Promocode) o;
        return Double.compare(discount, promocode.discount) == 0 && Objects.equals(id, promocode.id) && Objects.equals(promoStr, promocode.promoStr);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, promoStr, discount);
    }
}
