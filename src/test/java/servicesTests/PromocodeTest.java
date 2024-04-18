package servicesTests;

import dao.PromocodeDao;
import dto.PromocodeDto;
import mappers.PromocodeMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import services.PromocodeService;

@ExtendWith(MockitoExtension.class)
public class PromocodeTest {
    @InjectMocks
    PromocodeService promocodeService;
    @Mock
    PromocodeDao promocodeDao;
    @Mock
    PromocodeMapper promocodeMapper;
    @Mock
    PromocodeDto promocodeDto;

    // не используются(слишком много) доделаю в будущем
}
