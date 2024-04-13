package mappers;

import dto.PromocodeDto;
import entities.Promocode;

public class PromocodeMapper implements Imapper<Promocode, PromocodeDto> {
    @Override
    public  PromocodeDto map(Promocode promocode) {
        return new PromocodeDto(promocode.getPromoStr(),
                promocode.getDiscount());
    }
}
