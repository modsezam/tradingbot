package com.github.modsezam.service;

import com.github.modsezam.api.binance.BinanceApiWebSocketClient;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;

@Slf4j
public class BinanceClientWS {

    private final Closeable closeableClientWS;

    @Getter
    private final String pairSymbol;
    @Getter
    private String lastTradeEventPrice = "0";
    @Getter
    private long lastTradeEventTime = 0L;

    public BinanceClientWS(String pairSymbol, BinanceClientFactoryService binanceClientFactoryService) {
        this.pairSymbol = pairSymbol.toLowerCase();
        BinanceApiWebSocketClient clientWS = binanceClientFactoryService.getBinanceWSClientFactory().newWebSocketClient();
        closeableClientWS = clientWS.onAggTradeEvent(this.pairSymbol, response -> {
            lastTradeEventPrice = response.getPrice();
            lastTradeEventTime = response.getEventTime();
            log.debug("new event from pair {} - price {}", pairSymbol, response.getPrice());
        });
        log.info("create new Binance WS client - pair {}", pairSymbol);
    }

    public void closeClientWS() {
        try {
            closeableClientWS.close();
            log.info("close Binance client WS, pair - {}", pairSymbol.toUpperCase());
        } catch (IOException e) {
            e.printStackTrace();
            log.warn("exception - close Binance client WS. - {}", e.getMessage());
        }
    }

}
