package com.wenance.WenanceChallenge.services;

import com.wenance.WenanceChallenge.model.CEXResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class CEXService {
    private final String currencyURL = "https://cex.io";
    private final String baseUSD = "/api/last_price/BTC/USD";

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        restTemplate = builder.rootUri(currencyURL).build();

        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
        messageConverters.add(converter);

        restTemplate.setMessageConverters(messageConverters);

        return restTemplate;
    }

    public CEXResponse getCurrentValue() {

        return this.restTemplate
                .getForObject(baseUSD, CEXResponse.class);
    }
}
