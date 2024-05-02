package mappers;

import dao.GameDao;
import dao.KeyDao;
import dto.PurchaseDto;
import entities.Purchase;
import exceptions.DataBaseException;
import services.GameService;

public class CreatePurchaseMapper implements Imapper<PurchaseDto, Purchase> {
    GameService gameService = new GameService(new GameDao(), new KeyDao(), new GameMapper(), new CreateGameMapper(), new UpdateGameMapper());

    @Override
    public Purchase map(PurchaseDto purchaseDto) throws DataBaseException {
        return new Purchase(null, purchaseDto.getDateOfPurchase(),
                purchaseDto.getPromocodeid(),
                purchaseDto.getUserId(),
                gameService.findIdByTitle(purchaseDto.getGameTitle()),
                purchaseDto.getKeyStr());
    }
}
