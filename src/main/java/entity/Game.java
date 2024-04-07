package entity;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class Game {
    private Integer id;
    private String title;
    private String description;
    private double price;
    private LocalDate dateOfRelease;

    public Game(Integer id, String title, String description, double price, LocalDate dateOfRelease) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.dateOfRelease = dateOfRelease;
    }

    public Game() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public LocalDate getDateOfRelease() {
        return dateOfRelease;
    }

    public void setDateOfRelease(LocalDate dateOfRelease) {
        this.dateOfRelease = dateOfRelease;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Double.compare(price, game.price) == 0 &&
                Objects.equals(id, game.id) &&
                Objects.equals(title, game.title) &&
                Objects.equals(description, game.description) &&
                Objects.equals(dateOfRelease, game.dateOfRelease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, price, dateOfRelease);
    }
}
