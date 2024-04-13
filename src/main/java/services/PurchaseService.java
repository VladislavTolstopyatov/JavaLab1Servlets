package services;

import dao.PurchaseDao;
import dto.CreateUserDto;
import dto.PromocodeDto;
import dto.PurchaseDto;
import dto.UserDto;
import entities.Purchase;
import entities.User;
import exceptions.LoginAlreadyRegisteredException;
import mappers.PurchaseMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class PurchaseService {
    private final PurchaseDao purchaseDao = new PurchaseDao();
    private final PurchaseMapper purchaseMapper = new PurchaseMapper();
    private final GameService gameService = new GameService();

    List<PurchaseDto> findAll() {
        return purchaseDao.findAll().stream().map(purchaseMapper::map).toList();
    }

    public boolean deleteById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0!");
        }
        return purchaseDao.deleteById(id);
    }


    public PurchaseDto createPurchase(PurchaseDto purchaseDto) {
        return purchaseMapper.map(purchaseDao.save(new Purchase(null,
                purchaseDto.getDateOfPurchase(),
                purchaseDto.getPromocodeid(),
                purchaseDto.getUserId(),
                gameService.findIdByTitle(purchaseDto.getGameTitle()),
                purchaseDto.getKeyStr())));
    }

    public void updatePurchase(Purchase purchase) {
        purchaseDao.update(purchase);
    }

    public List<PurchaseDto> findByUserId(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0!");
        }
        return purchaseDao.findAllByUserID(id).
                stream().
                map(purchaseMapper::map).toList();
    }

    public List<PurchaseDto> findByGameId(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0!");
        }
        return purchaseDao.findAllByGameID(id).
                stream().
                map(purchaseMapper::map).toList();
    }

    public List<PurchaseDto> findByDate(LocalDate date) {
        return purchaseDao.findAllByPurchaseDate(date).
                stream().
                map(purchaseMapper::map).toList();
    }


    public PurchaseDto findById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id <= 0");
        }
        return purchaseMapper.map(purchaseDao.findById(id));
    }
}
