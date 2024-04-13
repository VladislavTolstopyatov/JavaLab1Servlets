package daoTests;

import dao.GameDao;
import dao.PurchaseDao;
import dao.UserDao;
import entities.*;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import exceptions.LoginAlreadyRegisteredException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class PurchaseDaoTest {
    GameDao gameDao = new GameDao();
    UserDao userDao = new UserDao();
    PurchaseDao purchaseDao = new PurchaseDao();

    Game game;
    User user;

    @BeforeEach
    void init() throws GameWithSuchTitleAlreadyExistsException, LoginAlreadyRegisteredException {
        game = gameDao.save(new Game(null, "TestGameForGame", "test", 200, LocalDate.now(),null));
        user = userDao.save(new User(null, "login", "password", 50, "88005553535", Role.USER, null));
    }

    @AfterEach
    void deleteFromDB() {
        userDao.deleteById(user.getId());
        gameDao.deleteById(game.getId());
    }

    @Test
    void savePurchaseTest() {
        Purchase purchase = new Purchase(null, LocalDate.now(), null,user.getId(),game.getId(),"test");
        Purchase purchaseCheck = purchaseDao.save(purchase);
        purchase.setId(purchaseCheck.getId());
        List<Purchase> purchases = purchaseDao.findAll();

        if (purchases.isEmpty()) {
            fail();
        }

        purchaseCheck = purchases.get(0);
        assertEquals(purchaseCheck, purchase);
    }

    @Test
    void findByIdPurchaseTest() {
        Purchase purchase = purchaseDao.save(new Purchase(null, LocalDate.now(), null, user.getId(),game.getId(),"test"));
        Purchase findPurchase = purchaseDao.findById(purchase.getId());
        assertEquals(purchase, findPurchase);
    }

    @Test
    void findAllPurchasesTest() {
        Purchase purchase1 = purchaseDao.save(new Purchase(null, LocalDate.now(), null,user.getId(), game.getId(), "test"));
        Purchase purchase2 = purchaseDao.save(new Purchase(null, LocalDate.now(), null,user.getId(),game.getId(),"test"));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase1);
        purchases.add(purchase2);

        List<Purchase> purchasesFromDao = purchaseDao.findAll();
        for (int i = 0; i < purchasesFromDao.size(); i++) {
            purchases.get(i).setId(purchasesFromDao.get(i).getId());
        }

        assertTrue(purchases.size() == purchasesFromDao.size() &&
                purchasesFromDao.containsAll(purchases) && purchases.containsAll(purchasesFromDao));
    }

    @Test
    void findAllByGameIdPurchaseTest() {
        Purchase purchase1 = purchaseDao.save(new Purchase(null, LocalDate.now(), null,user.getId(), game.getId(), "test"));
        Purchase purchase2 = purchaseDao.save(new Purchase(null, LocalDate.now(), null,user.getId(), game.getId(), "test"));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase1);
        purchases.add(purchase2);

        List<Purchase> purchasesFromDao = purchaseDao.findAllByGameID(game.getId());
        for (int i = 0; i < purchasesFromDao.size(); i++) {
            purchases.get(i).setId(purchasesFromDao.get(i).getId());
        }

        assertTrue(purchases.size() == purchasesFromDao.size() &&
                purchasesFromDao.containsAll(purchases) && purchases.containsAll(purchasesFromDao));
    }

    @Test
    void findAllByUserIdPurchaseTest() {

        Purchase purchase1 = purchaseDao.save(new Purchase(null, LocalDate.now(), null,user.getId(), game.getId(), "test"));
        Purchase purchase2 = purchaseDao.save(new Purchase(null, LocalDate.now(), null,user.getId(), game.getId(), "test"));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase1);
        purchases.add(purchase2);

        List<Purchase> purchasesFromDao = purchaseDao.findAllByUserID(user.getId());
        for (int i = 0; i < purchasesFromDao.size(); i++) {
            purchases.get(i).setId(purchasesFromDao.get(i).getId());
        }

        assertTrue(purchases.size() == purchasesFromDao.size() &&
                purchasesFromDao.containsAll(purchases) && purchases.containsAll(purchasesFromDao));
    }

    @Test
    void findAllByPurchaseDatePurchaseTest() {
        LocalDate localDate = LocalDate.of(2024, 3, 4);
        Purchase purchase1 = purchaseDao.save(new Purchase(null, LocalDate.now(), null,user.getId(), game.getId(), "test"));
        Purchase purchase2 = purchaseDao.save(new Purchase(null, LocalDate.now(), null,user.getId(), game.getId(), "test"));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(purchase1);
        purchases.add(purchase2);

        List<Purchase> purchasesFromDao = purchaseDao.findAllByPurchaseDate(localDate);
        for (int i = 0; i < purchasesFromDao.size(); i++) {
            purchases.get(i).setId(purchasesFromDao.get(i).getId());
        }

        assertTrue(purchases.size() == purchasesFromDao.size() &&
                purchasesFromDao.containsAll(purchases) && purchases.containsAll(purchasesFromDao));
    }

    @Test
    void updatePurchaseTest() {
        Purchase purchaseFirst = new Purchase(null, LocalDate.now(), null,user.getId(), game.getId(), "test");
        Purchase purchase = purchaseDao.save(purchaseFirst);
        purchaseFirst.setId(purchase.getId());

        purchaseFirst.setDateOfPurchase(LocalDate.now());
        purchaseFirst.setPromocodeId(null);

        purchaseDao.update(purchaseFirst);

        purchase = purchaseDao.findById(purchaseFirst.getId());
        assertEquals(purchase, purchaseFirst);
    }

    @Test
    void deleteByIdPurchaseTest() {
        Purchase purchase = purchaseDao.save(new Purchase(null, LocalDate.now(), null,user.getId(), game.getId(), "test"));
        purchaseDao.deleteById(purchase.getId());
        assertNull(purchaseDao.findById(purchase.getId()));
    }
}
