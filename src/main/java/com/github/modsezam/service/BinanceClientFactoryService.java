package com.github.modsezam.service;

import com.github.modsezam.api.binance.BinanceApiClientFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BinanceClientFactoryService {

    @Value("${binance.api.key}")
    private String apiKey;

    @Value("${binance.api.secret}")
    private String apiSecret;

    public BinanceApiClientFactory getBinanceClientFactory() {
        return BinanceApiClientFactory.newInstance(apiKey, apiSecret);
    }

    public BinanceApiClientFactory getBinanceWSClientFactory() {
        return BinanceApiClientFactory.newInstance();
    }


}
