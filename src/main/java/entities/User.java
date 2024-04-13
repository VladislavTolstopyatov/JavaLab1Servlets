package entities;

import java.util.List;
import java.util.Objects;

public class User {
    private Integer id;
    private String login;
    private String password;
    private double balance;
    private String cardNumber;
    private Role role;
    private List<Purchase> purchases;

    public User() {

    }

    public User(Integer id, String login, String password, double balance, String cardNumber, Role role, List<Purchase> purchases) {
        this.id = id;
        this.login = login;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Double.compare(balance, user.balance) == 0 && Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(password, user.password) && Objects.equals(cardNumber, user.cardNumber) && role == user.role && Objects.equals(purchases, user.purchases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, password, balance, cardNumber, role, purchases);
    }
}
