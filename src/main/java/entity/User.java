package entity;

import java.util.List;
import java.util.Objects;

public class User {
    private Integer id;
    private String login;
    private int Password;
    private double balance;
    private String cardNumber;
    private Role role;
    private List<Purchase> purchases;

    public User(Integer id, String login, int password, double balance, String cardNumber, Role role, List<Purchase> purchases) {
        this.id = id;
        this.login = login;
        Password = password;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.role = role;
        this.purchases = purchases;
    }

    public User() {
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

    public int getPassword() {
        return Password;
    }

    public void setPassword(int password) {
        Password = password;
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
        return Password == user.Password &&
                Double.compare(balance, user.balance) == 0 &&
                Objects.equals(id, user.id) &&
                Objects.equals(login, user.login) &&
                Objects.equals(cardNumber, user.cardNumber) &&
                role == user.role && Objects.equals(purchases, user.purchases);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, login, Password, balance, cardNumber, role, purchases);
    }
}
