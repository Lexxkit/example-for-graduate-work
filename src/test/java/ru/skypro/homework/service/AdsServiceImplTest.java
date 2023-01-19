package ru.skypro.homework.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateAdsDto;
import ru.skypro.homework.dto.ResponseWrapperAds;
import ru.skypro.homework.entity.Ads;
import ru.skypro.homework.entity.User;
import ru.skypro.homework.mapper.AdsMapper;
import ru.skypro.homework.mapper.AdsMapperImpl;
import ru.skypro.homework.repository.AdsRepository;
import ru.skypro.homework.service.impl.AdsServiceImpl;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdsServiceImplTest {
    @Mock
    private AdsRepository adsRepository;
    @Spy
    private AdsMapper adsMapper = new AdsMapperImpl();
    @InjectMocks
    private AdsServiceImpl out;

    private List<Ads> adsList;
    private Ads ads1;
    private Ads ads2;
    private CreateAdsDto createAdsDto;
    private User testUser;
    @BeforeEach
    void init() {
        testUser = new User();
        testUser.setId(42L);

        createAdsDto = new CreateAdsDto();
        createAdsDto.setDescription("Test description");
        createAdsDto.setTitle("Test title");
        createAdsDto.setPrice(10);

        ads1 = new Ads();
        ads1.setId(1L);
        ads1.setPrice(new BigDecimal(10));
        ads1.setTitle("Test ads");
        ads1.setAuthor(testUser);

        ads2 = new Ads();
        ads2.setId(2L);
        ads2.setPrice(new BigDecimal(20));
        ads2.setTitle("Test ads 2");
        ads2.setAuthor(testUser);

        adsList = List.of(ads1, ads2);
    }

    @Test
    void shouldReturnResponseWrapperAdsWithAllAds() {
        when(adsRepository.findAll()).thenReturn(adsList);
        ResponseWrapperAds result = out.getAllAds();
        System.out.println(result);

        assertThat(result).isNotNull();
        assertThat(result.getCount()).isEqualTo(adsList.size());
        assertThat(result.getResults()).isNotNull();
    }

    @Test
    void shouldReturnAdsDtoFromCreateAdsDto() {
        Ads adsForMockSave  = adsMapper.createAdsDtoToAds(createAdsDto);
        when(adsRepository.save(any(Ads.class))).thenReturn(adsForMockSave);
        AdsDto result = out.createAds(createAdsDto, null);

        assertThat(result).isNotNull();
        assertThat(result.getTitle()).isEqualTo(createAdsDto.getTitle());
        assertThat(result.getPrice()).isEqualTo(createAdsDto.getPrice());
    }

}