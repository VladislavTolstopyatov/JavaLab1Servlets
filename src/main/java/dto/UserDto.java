package dto;

import entities.Purchase;
import entities.Role;

import java.util.List;

public class UserDto {
    private Integer id;
    private String login;
    private double balance;
    private String cardNumber;
    private Role role;
    private List<PurchaseDto> purchases;

    public UserDto(Integer id, String login, double balance, String cardNumber, Role role, List<PurchaseDto> purchases) {
        this.id = id;
        this.login = login;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.role = role;
        this.purchases = purchases;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<PurchaseDto> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<PurchaseDto> purchases) {
        this.purchases = purchases;
    }
}
