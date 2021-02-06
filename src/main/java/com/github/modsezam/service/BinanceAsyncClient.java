package com.github.modsezam.service;

import com.github.modsezam.api.binance.BinanceApiAsyncRestClient;
import com.github.modsezam.api.binance.BinanceApiRestClient;
import com.github.modsezam.api.binance.domain.account.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class BinanceAsyncClient {

    private final BinanceApiAsyncRestClient asyncClient;

    public BinanceAsyncClient(BinanceClientFactoryService binanceClientFactoryService) {
        asyncClient = binanceClientFactoryService.getAsyncClient();
    }

    public Account getAccount() {

        asyncClient.get24HrPriceStatistics("BTCUSDT", response -> System.out.println(response.getCount()));

        return null;
    }

}
