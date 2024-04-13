package dto;

import entities.Role;

public class CreateUserDto {
    private String login;
    private String Password;
    private double balance;
    private String cardNumber;
    private Role role;

    public CreateUserDto(String login, String password, double balance, String cardNumber, Role role) {
        this.login = login;
        Password = password;
        this.balance = balance;
        this.cardNumber = cardNumber;
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
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
}
