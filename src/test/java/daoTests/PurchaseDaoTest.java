package daoTests;

import dao.GameDao;
import dao.KeyDao;
import dao.PurchaseDao;
import dao.UserDao;
import entity.*;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import exceptions.LoginAlreadyRegisteredException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseDaoTest {
    static GameDao gameDao;
    static UserDao userDao;
    static Game game;
    static User user;
    PurchaseDao purchaseDao = new PurchaseDao();

    static {

        gameDao = new GameDao();
        userDao = new UserDao();
        try {
            game = gameDao.save(new Game(null, "TestGameForGame", "test", 200, LocalDate.now()));
            user = userDao.save(new User(null, "login", Objects.hash("password"), 50, "88005553535", Role.USER, null));
        } catch (GameWithSuchTitleAlreadyExistsException | LoginAlreadyRegisteredException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void savePurchaseTest() {
        Purchase purchase = new Purchase(null, LocalDate.now(), null, game.getId(), user.getId());

        Purchase purchaseCheck = purchaseDao.save(purchase);

        purchase.setId(purchaseCheck.getId());

        List<Purchase> purchases = purchaseDao.findAll();

        if (purchases.isEmpty()) {
            fail();
        }

        purchaseCheck = purchases.get(0);

        assertEquals(purchaseCheck, purchase);
        gameDao.deleteById(game.getId());
        userDao.deleteById(user.getId());
    }

    @Test
    void findByIdPurchaseTest() {
        Purchase purchase = purchaseDao.save(new Purchase(null, LocalDate.now(), null, game.getId(), user.getId()));
        Purchase findPurchase = purchaseDao.findById(purchase.getId());
        assertEquals(purchase, findPurchase);
        gameDao.deleteById(game.getId());
        userDao.deleteById(user.getId());
    }

    @Test
    void findAllPurchasesTest() {
        Purchase purchase1 = purchaseDao.save(new Purchase(null, LocalDate.now(), null, game.getId(), user.getId()));
        Purchase purchase2 = purchaseDao.save(new Purchase(null, LocalDate.now(), null, game.getId(), user.getId()));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase1);
        purchases.add(purchase2);

        List<Purchase> purchasesFromDao = purchaseDao.findAll();
        for (int i = 0; i < purchasesFromDao.size(); i++) {
            purchases.get(i).setId(purchasesFromDao.get(i).getId());
        }

        assertTrue(purchases.size() == purchasesFromDao.size() &&
                purchasesFromDao.containsAll(purchases) && purchases.containsAll(purchasesFromDao));

        gameDao.deleteById(game.getId());
        userDao.deleteById(user.getId());
    }

    @Test
    void findAllByGameIdPurchaseTest() {
        Purchase purchase1 = purchaseDao.save(new Purchase(null, LocalDate.now(), null, game.getId(), user.getId()));
        Purchase purchase2 = purchaseDao.save(new Purchase(null, LocalDate.now(), null, game.getId(), user.getId()));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase1);
        purchases.add(purchase2);

        List<Purchase> purchasesFromDao = purchaseDao.findAllByGameID(game.getId());
        for (int i = 0; i < purchasesFromDao.size(); i++) {
            purchases.get(i).setId(purchasesFromDao.get(i).getId());
        }

        assertTrue(purchases.size() == purchasesFromDao.size() &&
                purchasesFromDao.containsAll(purchases) && purchases.containsAll(purchasesFromDao));

        gameDao.deleteById(game.getId());
        userDao.deleteById(user.getId());
    }

    @Test
    void findAllByUserIdPurchaseTest() {

        Purchase purchase1 = purchaseDao.save(new Purchase(null, LocalDate.now(), null, game.getId(), user.getId()));
        Purchase purchase2 = purchaseDao.save(new Purchase(null, LocalDate.now(), null, game.getId(), user.getId()));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase1);
        purchases.add(purchase2);

        List<Purchase> purchasesFromDao = purchaseDao.findAllByUserID(user.getId());
        for (int i = 0; i < purchasesFromDao.size(); i++) {
            purchases.get(i).setId(purchasesFromDao.get(i).getId());
        }

        assertTrue(purchases.size() == purchasesFromDao.size() &&
                purchasesFromDao.containsAll(purchases) && purchases.containsAll(purchasesFromDao));

        gameDao.deleteById(game.getId());
        userDao.deleteById(user.getId());
    }

    @Test
    void findAllByPurchaseDatePurchaseTest() {
        LocalDate localDate = LocalDate.of(2024, 3, 4);
        Purchase purchase1 = purchaseDao.save(new Purchase(null, localDate, null, game.getId(), user.getId()));
        Purchase purchase2 = purchaseDao.save(new Purchase(null, localDate, null, game.getId(), user.getId()));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase1);
        purchases.add(purchase2);

        List<Purchase> purchasesFromDao = purchaseDao.findAllByPurchaseDate(localDate);
        for (int i = 0; i < purchasesFromDao.size(); i++) {
            purchases.get(i).setId(purchasesFromDao.get(i).getId());
        }

        assertTrue(purchases.size() == purchasesFromDao.size() &&
                purchasesFromDao.containsAll(purchases) && purchases.containsAll(purchasesFromDao));

        gameDao.deleteById(game.getId());
        userDao.deleteById(user.getId());
    }

    @Test
    void updatePurchaseTest() {
        Purchase purchaseFirst = new Purchase(null, LocalDate.now(), null, game.getId(), user.getId());
        Purchase purchase = purchaseDao.save(purchaseFirst);
        purchaseFirst.setId(purchase.getId());

        purchaseFirst.setDateOfPurchase(LocalDate.now());
        purchaseFirst.setPromocode_id(null);

        purchaseDao.update(purchaseFirst);

        purchase = purchaseDao.findById(purchaseFirst.getId());
        assertEquals(purchase, purchaseFirst);

        gameDao.deleteById(game.getId());
        userDao.deleteById(user.getId());

    }

    @Test
    void deleteByIdPurchaseTest() {
        Purchase purchase = purchaseDao.save(new Purchase(null, LocalDate.now(), null, game.getId(), user.getId()));
        purchaseDao.deleteById(purchase.getId());
        assertNull(purchaseDao.findById(purchase.getId()));
        gameDao.deleteById(game.getId());
        userDao.deleteById(user.getId());
    }
}
