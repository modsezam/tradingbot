package com.github.modsezam.service;

import com.github.modsezam.api.binance.BinanceApiAsyncRestClient;
import com.github.modsezam.api.binance.BinanceApiClientFactory;
import com.github.modsezam.api.binance.impl.BinanceApiAsyncRestClientImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class BinanceClientFactoryService {

    @Value("${binance.api.key}")
    private String apiKey;

    @Value("${binance.api.secret}")
    private String apiSecret;

    public BinanceApiClientFactory getBinanceClient() {
        return BinanceApiClientFactory.newInstance(apiKey, apiSecret);
    }

    public BinanceApiClientFactory getBinanceWSClient() {
        return BinanceApiClientFactory.newInstance();
    }

    public BinanceApiAsyncRestClient getAsyncClient(){
        return BinanceApiClientFactory.newInstance(apiKey, apiSecret).newAsyncRestClient();
    }
}
