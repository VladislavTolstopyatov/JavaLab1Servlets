package services;

import dao.PurchaseDao;
import dto.PurchaseDto;
import entities.Purchase;
import exceptions.DataBaseException;
import mappers.CreatePurchaseMapper;
import mappers.PurchaseMapper;

import java.time.LocalDate;
import java.util.List;

public class PurchaseService {
    private final PurchaseDao purchaseDao;
    private final PurchaseMapper purchaseMapper;
    private final GameService gameService;
    private final CreatePurchaseMapper createPurchaseMapper;

    public PurchaseService(PurchaseDao purchaseDao, PurchaseMapper purchaseMapper, GameService gameService, CreatePurchaseMapper createPurchaseMapper) {
        this.purchaseDao = purchaseDao;
        this.purchaseMapper = purchaseMapper;
        this.gameService = gameService;
        this.createPurchaseMapper = createPurchaseMapper;
    }

    public List<PurchaseDto> findAll() throws DataBaseException {
        return purchaseDao.findAll().stream().map(purchaseMapper::map).toList();
    }

    public boolean deleteById(Integer id) throws DataBaseException {
        return purchaseDao.deleteById(id);
    }


    public PurchaseDto createPurchase(PurchaseDto purchaseDto) throws DataBaseException {
        Purchase purchase = purchaseDao.save(createPurchaseMapper.map(purchaseDto));
        return purchaseMapper.map(purchase);
    }

    public void updatePurchase(Purchase purchase) throws DataBaseException {
        purchaseDao.update(purchase);
    }

    public List<PurchaseDto> findByUserId(Integer id) throws DataBaseException {
        return purchaseDao.findAllByUserID(id).
                stream().
                map(purchaseMapper::map).toList();
    }

    public List<PurchaseDto> findByGameId(Integer id) throws DataBaseException {
        return purchaseDao.findAllByGameID(id).
                stream().
                map(purchaseMapper::map).toList();
    }

    public List<PurchaseDto> findByDate(LocalDate date) throws DataBaseException {
        return purchaseDao.findAllByPurchaseDate(date).
                stream().
                map(purchaseMapper::map).toList();
    }


    public PurchaseDto findById(Integer id) throws DataBaseException {
        return purchaseMapper.map(purchaseDao.findById(id));
    }
}
