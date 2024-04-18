package servicesTests;

import dao.PurchaseDao;
import dto.KeyDto;
import dto.PurchaseDto;
import entities.Key;
import mappers.PurchaseMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.PurchaseService;

@ExtendWith(MockitoExtension.class)
public class PurchaseServiceTest {
    @InjectMocks
    PurchaseService purchaseService;
    @Mock
    PurchaseDao purchaseDao;
    @Mock
    PurchaseMapper purchaseMapper;
    @Mock
    PurchaseDto purchaseDto;


}
