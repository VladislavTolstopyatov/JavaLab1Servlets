package mappers;

import dto.PurchaseDto;
import entities.Purchase;
import services.GameService;

public class PurchaseMapper {
    private GameService gameService = new GameService();
    public PurchaseDto map(Purchase purchase) {
        return new PurchaseDto(purchase.getDateOfPurchase(),
                purchase.getPromocodeId(),
                gameService.findTitleById(purchase.getGameId()),
                purchase.getUserId(),
                purchase.getKeyStr());
    }
}
