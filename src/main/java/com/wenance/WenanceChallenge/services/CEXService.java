package com.wenance.WenanceChallenge.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wenance.WenanceChallenge.dto.CEXResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class CEXService {
    private final String currencyURL = "https://cex.io";
    private final String baseUSD = "/api/last_price/BTC/USD";

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    public CEXResponse getCurrentValue() {
        return this.webClientBuilder.codecs(clientDefaultCodecsConfigurer -> {
                clientDefaultCodecsConfigurer
                        .defaultCodecs()
                        .jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper(), MediaType.ALL));
                clientDefaultCodecsConfigurer
                        .defaultCodecs()
                        .jackson2JsonDecoder(new Jackson2JsonDecoder(new ObjectMapper(), MediaType.ALL));
            }).baseUrl(currencyURL).build()
                .get().uri(baseUSD).accept(MediaType.ALL)
                .retrieve().bodyToMono(CEXResponse.class).block();
    }
}
