package services;

import dao.PromocodeDao;
import dto.PromocodeDto;
import entities.Game;
import entities.Promocode;
import exceptions.DataBaseException;
import exceptions.GameWithSuchTitleAlreadyExistsException;
import mappers.PromocodeMapper;

import java.util.List;

public class PromocodeService {
    private final PromocodeDao promocodeDao;
    private final PromocodeMapper promocodeMapper;

    public PromocodeService(PromocodeDao promocodeDao, PromocodeMapper promocodeMapper) {
        this.promocodeDao = promocodeDao;
        this.promocodeMapper = promocodeMapper;
    }

    public PromocodeDto findById(Integer id) throws DataBaseException {
        return promocodeMapper.map(promocodeDao.findById(id));
    }

    public PromocodeDto findByPromoStr(String promoStr) throws DataBaseException {
        return promocodeMapper.map(promocodeDao.findByPromocode(promoStr));
    }

    public boolean deleteById(Integer id) throws DataBaseException {
        return promocodeDao.deleteById(id);
    }

    public Promocode createPromocode(PromocodeDto promocodeDto) throws DataBaseException {
        return promocodeDao.save(new Promocode(null,
                promocodeDto.getPromoStr(),
                promocodeDto.getDiscount()));
    }

    public List<PromocodeDto> getAll() throws DataBaseException {
        List<Promocode> getAll = promocodeDao.findAll();
        return getAll.stream().map(promocodeMapper::map).toList();
    }

    public void update(Promocode promocode) throws DataBaseException {
        promocodeDao.update(promocode);
    }
}
