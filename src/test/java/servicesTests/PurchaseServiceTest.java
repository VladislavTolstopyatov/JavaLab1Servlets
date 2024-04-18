package servicesTests;

import dao.PurchaseDao;
import dto.GameDto;
import dto.KeyDto;
import dto.PurchaseDto;
import entities.Game;
import entities.Key;
import entities.Purchase;
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
    public void findByIdWhenPurchaseExists() {
        Purchase purchase = getPurchase();
        PurchaseDto purchaseDto = getPurchaseDto();

        Integer id = purchaseDto.getId();

        when(purchaseDao.findById(id)).thenReturn(purchase);
        when(purchaseMapper.map(purchase)).thenReturn(purchaseDto);

        assertThat(purchaseService.findById(id)).isEqualTo(purchaseDto);
    }

    @Test
    public void findByIdWhenPurchaseNotExists() {
        PurchaseDto purchaseDto = getPurchaseDto();
        Integer id = purchaseDto.getId();

        when(purchaseDao.findById(id)).thenReturn(null);
        assertThat(purchaseService.findById(id)).isEqualTo(null);
    }

    @Test
    public void findAllWhenPurchasesExists() {
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
    public void findAllWhenPurchasesNotExists() {
        when(purchaseDao.findAll())
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> purchaseService.findAll());
    }

    @Test
    void createPurchase() {
        Purchase purchase = getPurchase();
        PurchaseDto purchaseDto = getPurchaseDto();
        Integer id = purchaseDto.getId();

        when(createPurchaseMapper.map(purchaseDto)).thenReturn(purchase);
        when(purchaseMapper.map(purchase)).thenReturn(purchaseDto);

        assertThat(purchaseService.createPurchase(purchaseDto)).isEqualTo(purchaseDto);
    }

    @Test
    void findAllByGameIdWhenPurchasesExists() {
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
    void findAllByGameIdWhenPurchasesNotExists() {
        Integer id = getPurchase().getId();
        when(purchaseDao.findAllByGameID(id))
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> purchaseService.findByGameId(id));
    }

    @Test
    void findAllByUserIdWhenPurchasesExists() {
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
    void findAllByUserIdWhenPurchasesNotExists() {
        Integer id = getPurchaseDto().getUserId();
        when(purchaseDao.findAllByUserID(id))
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> purchaseService.findByUserId(id));
    }

    @Test
    void findAllByDateWhenPurchasesExists() {
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
    void findAllByDatedWhenPurchasesNotExists() {
        LocalDate localDate = getPurchaseDto().getDateOfPurchase();
        when(purchaseDao.findAllByPurchaseDate(localDate))
                .thenReturn(null);
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> purchaseService.findByDate(localDate));
    }

    @Test
    void deleteByIdWhenGameExists() {
        Integer id = 1;
        when(purchaseDao.deleteById(id)).thenReturn(true);
        Assertions.assertThat(purchaseService.deleteById(id)).isEqualTo(true);
    }

    @Test
    void deleteByIdWhenGameNotExists() {
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
