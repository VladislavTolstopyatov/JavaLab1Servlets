package mappers;

import dao.GameDao;
import dao.KeyDao;
import dto.PurchaseDto;
import entities.Purchase;
import services.GameService;

public class PurchaseMapper {
    private GameService gameService = new GameService(new GameDao(), new KeyDao(), new GameMapper(),
            new CreateGameMapper(), new UpdateGameMapper());

    public PurchaseDto map(Purchase purchase) {
        return new PurchaseDto(purchase.getDateOfPurchase(),
                purchase.getPromocodeId(),
                gameService.findTitleById(purchase.getGameId()),
                purchase.getUserId(),
                purchase.getKeyStr());
    }
}
