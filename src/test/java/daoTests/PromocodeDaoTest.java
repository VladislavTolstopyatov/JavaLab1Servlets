package daoTests;

import dao.PromocodeDao;
import entity.Promocode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PromocodeDaoTest {
    PromocodeDao promocodeDao = new PromocodeDao();

    @Test
    void SavePromocodeTest() {
        Promocode testPromocode = new Promocode(null, "2136161", 10);
        promocodeDao.save(testPromocode);
        List<Promocode> promocodes = promocodeDao.findAll();
        if (promocodes.isEmpty()) {
            fail();
        }
        Promocode promocode = promocodes.get(0);
        testPromocode.setId(promocode.getId());
        assertEquals(testPromocode, promocode);
    }

    @Test
    void findByIdTest() {
        Promocode promocodeFromDao = promocodeDao.save(new Promocode(null, "promo", 10));
        Promocode promocode = promocodeDao.findById(promocodeFromDao.getId());
        assertEquals(promocodeFromDao, promocode);
    }

    @Test
    void findAllPromocodes() {
        Promocode promocode1 = new Promocode(null, "tyes1", 10);
        Promocode promocode2 = new Promocode(null, "dsahg", 10);

        List<Promocode> promocodes = new ArrayList<>();
        promocodes.add(promocode1);
        promocodes.add(promocode2);

        promocodeDao.save(promocode1);
        promocodeDao.save(promocode2);

        List<Promocode> promocodesFromDao = promocodeDao.findAll();
        for (int i = 0; i < promocodesFromDao.size(); i++) {
            promocodes.get(i).setId(promocodesFromDao.get(i).getId());
        }

        assertTrue(promocodes.size() == promocodesFromDao.size() &&
                promocodesFromDao.containsAll(promocodes) && promocodes.containsAll(promocodesFromDao));
    }

    @Test
    void updatePromocodeTest() {
        Promocode promocodeToInsert = new Promocode(null, "12515", 10);
        Promocode promocodeFromDao = promocodeDao.save(promocodeToInsert);
        promocodeToInsert.setId(promocodeFromDao.getId());

        promocodeToInsert.setPromoStr("update");
        promocodeToInsert.setDiscount(15);

        promocodeDao.update(promocodeToInsert);
        promocodeFromDao = promocodeDao.findById(promocodeToInsert.getId());

        assertEquals(promocodeFromDao, promocodeToInsert);
    }

    @Test
    void deleteByIdPromocodeTest() {
        Promocode promocode = promocodeDao.save(new Promocode(null, "yesy", 1));
        promocodeDao.deleteById(promocode.getId());
        assertNull(promocodeDao.findById(promocode.getId()));
    }

    @Test
    void findByPromocodeStrTest() {
        Promocode promocode = promocodeDao.save(new Promocode(null, "test", 1));
        Promocode promocodeFromDao = promocodeDao.findByPromocode(promocode.getPromoStr());
        assertEquals(promocode.getPromoStr(), promocodeFromDao.getPromoStr());
    }
}
