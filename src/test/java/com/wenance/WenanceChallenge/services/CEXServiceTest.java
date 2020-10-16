package com.wenance.WenanceChallenge.services;

import com.wenance.WenanceChallenge.dto.CEXResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@SpringBootTest
public class CEXServiceTest {
    @Mock
    RestTemplate template;

    @InjectMocks
    CEXService service;

    @Test
    public void getCurrentValueTest_ShouldBeSuccess(){
        final CEXResponse response = new CEXResponse();
        response.setCurr1("BTK");
        response.setCurr2("USD");
        response.setLprice(BigDecimal.TEN);
        when(template.getForObject("/api/last_price/BTC/USD", CEXResponse.class)).thenReturn(response);

        final CEXResponse value = service.getCurrentValue();
        assertThat(value.getLprice()).isEqualTo(BigDecimal.TEN);
    }
}
