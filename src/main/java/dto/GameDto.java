package dto;

import entities.Key;

import java.time.LocalDate;
import java.util.List;

public class GameDto {
    private Integer id;
    private String title;
    private String description;
    private double price;
    private LocalDate dateOfRelease;
    private List<Key> keys;
    private Integer keysCount;

    public GameDto(Integer id, String title, String description, double price, LocalDate dateOfRelease, List<Key> keys, Integer keysCount) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.price = price;
        this.dateOfRelease = dateOfRelease;
        this.keys = keys;
        this.keysCount = keysCount;
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

    public List<Key> getKeys() {
        return keys;
    }

    public void setKeys(List<Key> keys) {
        this.keys = keys;
    }

    public Integer getKeysCount() {
        return keysCount;
    }

    public void setKeysCount(Integer keysCount) {
        this.keysCount = keysCount;
    }
}
