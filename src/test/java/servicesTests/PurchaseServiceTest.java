package servicesTests;

import dao.PurchaseDao;
import dto.GameDto;
import dto.KeyDto;
import dto.PurchaseDto;
import entities.Game;
import entities.Key;
import entities.Purchase;
import exceptions.DataBaseException;
import mappers.CreatePurchaseMapper;
import mappers.PurchaseMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.PurchaseService;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {
    @InjectMocks
    PurchaseService purchaseService;
    @Mock
    PurchaseDao purchaseDao;
    @Mock
    PurchaseMapper purchaseMapper;

    @Mock
    CreatePurchaseMapper createPurchaseMapper;

    @Test
    public void findByIdWhenPurchaseExists() throws DataBaseException {
        Purchase purchase = getPurchase();
        PurchaseDto purchaseDto = getPurchaseDto();

        Integer id = purchaseDto.getId();

        when(purchaseDao.findById(id)).thenReturn(purchase);
        when(purchaseMapper.map(purchase)).thenReturn(purchaseDto);

        assertThat(purchaseService.findById(id)).isEqualTo(purchaseDto);
    }

    @Test
    public void findByIdWhenPurchaseNotExists() throws DataBaseException {
        PurchaseDto purchaseDto = getPurchaseDto();
        Integer id = purchaseDto.getId();

        when(purchaseDao.findById(id)).thenReturn(null);
        assertThat(purchaseService.findById(id)).isEqualTo(null);
    }

    @Test
    public void findAllWhenPurchasesExists() throws DataBaseException {
        Purchase purchase = getPurchase();
        List<Purchase> purchases = List.of(purchase);
        PurchaseDto purchaseDto = getPurchaseDto();

        when(purchaseDao.findAll())
                .thenReturn(purchases);
        when(purchaseMapper.map(purchase))
                .thenReturn(purchaseDto);

        Assertions.assertThat(purchaseService.findAll())
                .hasSize(1);
        Assertions.assertThat(purchaseService.findAll().get(0).getId()).
                isEqualTo(purchaseDto.getId());
    }

    @Test
    public void findAllWhenPurchasesNotExists() throws DataBaseException {
        when(purchaseDao.findAll())
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> purchaseService.findAll());
    }

    @Test
    void createPurchase() throws DataBaseException {
        Purchase purchase = getPurchase();
        PurchaseDto purchaseDto = getPurchaseDto();
        Integer id = purchaseDto.getId();

        when(createPurchaseMapper.map(purchaseDto)).thenReturn(purchase);
        when(purchaseMapper.map(purchase)).thenReturn(purchaseDto);

        assertThat(purchaseService.createPurchase(purchaseDto)).isEqualTo(purchaseDto);
    }

    @Test
    void findAllByGameIdWhenPurchasesExists() throws DataBaseException {
        Purchase purchase = getPurchase();
        List<Purchase> purchases = List.of(purchase);
        PurchaseDto purchaseDto = getPurchaseDto();

        Integer id = purchase.getId();

        when(purchaseDao.findAllByGameID(id))
                .thenReturn(purchases);
        when(purchaseMapper.map(purchase))
                .thenReturn(purchaseDto);

        Assertions.assertThat(purchaseService.findByGameId(id))
                .hasSize(1);
        Assertions.assertThat(purchaseService.findByGameId(id).get(0).getId()).
                isEqualTo(purchaseDto.getId());
    }

    @Test
    void findAllByGameIdWhenPurchasesNotExists() throws DataBaseException {
        Integer id = getPurchase().getId();
        when(purchaseDao.findAllByGameID(id))
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> purchaseService.findByGameId(id));
    }

    @Test
    void findAllByUserIdWhenPurchasesExists() throws DataBaseException {
        Purchase purchase = getPurchase();
        List<Purchase> purchases = List.of(purchase);
        PurchaseDto purchaseDto = getPurchaseDto();

        Integer id = purchaseDto.getUserId();

        when(purchaseDao.findAllByUserID(id))
                .thenReturn(purchases);
        when(purchaseMapper.map(purchase))
                .thenReturn(purchaseDto);

        Assertions.assertThat(purchaseService.findByUserId(id))
                .hasSize(1);
        Assertions.assertThat(purchaseService.findByUserId(id).get(0).getId()).
                isEqualTo(purchaseDto.getId());
    }

    @Test
    void findAllByUserIdWhenPurchasesNotExists() throws DataBaseException {
        Integer id = getPurchaseDto().getUserId();
        when(purchaseDao.findAllByUserID(id))
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> purchaseService.findByUserId(id));
    }

    @Test
    void findAllByDateWhenPurchasesExists() throws DataBaseException {
        Purchase purchase = getPurchase();
        List<Purchase> purchases = List.of(purchase);
        PurchaseDto purchaseDto = getPurchaseDto();

        LocalDate localDate = purchaseDto.getDateOfPurchase();

        when(purchaseDao.findAllByPurchaseDate(localDate))
                .thenReturn(purchases);
        when(purchaseMapper.map(purchase))
                .thenReturn(purchaseDto);

        Assertions.assertThat(purchaseService.findByDate(localDate))
                .hasSize(1);
        Assertions.assertThat(purchaseService.findByDate(localDate).get(0).getId()).
                isEqualTo(purchaseDto.getId());
    }

    @Test
    void findAllByDatedWhenPurchasesNotExists() throws DataBaseException {
        LocalDate localDate = getPurchaseDto().getDateOfPurchase();
        when(purchaseDao.findAllByPurchaseDate(localDate))
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> purchaseService.findByDate(localDate));
    }

    @Test
    void deleteByIdWhenGameExists() throws DataBaseException {
        Integer id = 1;
        when(purchaseDao.deleteById(id)).thenReturn(true);
        Assertions.assertThat(purchaseService.deleteById(id)).isEqualTo(true);
    }

    @Test
    void deleteByIdWhenGameNotExists() throws DataBaseException {
        Integer id = 1;
        when(purchaseDao.deleteById(id)).thenReturn(false);
        Assertions.assertThat(purchaseService.deleteById(id)).isEqualTo(false);
    }

    private static PurchaseDto getPurchaseDto() {
        return new PurchaseDto(1,
                LocalDate.of(2003, 3, 3),
                1,
                "test",
                1,
                "test");
    }

    private static Purchase getPurchase() {
        return new Purchase(1,
                LocalDate.of(2003, 3, 3),
                1,
                1,
                1,
                "test");
    }
}
