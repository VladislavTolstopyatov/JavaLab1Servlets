package services;

import dao.PromocodeDao;
import dto.PromocodeDto;
import entities.Game;
import entities.Promocode;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import mappers.PromocodeMapper;

import java.util.List;

public class PromocodeService {
    private final PromocodeDao promocodeDao = new PromocodeDao();
    private final PromocodeMapper promocodeMapper = new PromocodeMapper();

    public PromocodeDto findById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id < 0!");
        }
        return promocodeMapper.map(promocodeDao.findById(id));
    }

    public PromocodeDto findByPromoStr(String promoStr) {
        if (promoStr.isEmpty()) {
            throw new IllegalArgumentException("promoStr is empty!");
        }
        return promocodeMapper.map(promocodeDao.findByPromocode(promoStr));
    }

    public boolean deleteById(Integer id) {
        if (id <= 0) {
            throw new IllegalArgumentException("id < 0!");
        }
        return promocodeDao.deleteById(id);
    }

    public Promocode createPromocode(PromocodeDto promocodeDto) {
        return promocodeDao.save(new Promocode(null,
                promocodeDto.getPromoStr(),
                promocodeDto.getDiscount()));
    }

    public List<PromocodeDto> getAll() {
        List<Promocode> getAll = promocodeDao.findAll();
        return getAll.stream().map(promocodeMapper::map).toList();
    }

    public void update(Promocode promocode) {
        promocodeDao.update(promocode);
    }
}
