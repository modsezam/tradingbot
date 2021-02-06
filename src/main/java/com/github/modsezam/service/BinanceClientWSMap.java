package com.github.modsezam.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class BinanceClientWSMap {

    private final BinanceClientFactoryService binanceClientFactoryService;
    private final Map<String, BinanceClientWS> binanceClientWSMap;

    public BinanceClientWSMap(BinanceClientFactoryService binanceClientFactoryService) {
        binanceClientWSMap = new HashMap<>();
        this.binanceClientFactoryService = binanceClientFactoryService;
    }

    public void createNewBinanceClientWS(String pairSymbol){
        binanceClientWSMap.put(pairSymbol, new BinanceClientWS(pairSymbol, binanceClientFactoryService));
    }

    public void closeBinanceClientWS(String pairSymbol){
        binanceClientWSMap.get(pairSymbol).closeClientWS();
        binanceClientWSMap.remove(pairSymbol);
    }

    public String getLastTradeEventPrice(String pairSymbol) {
        BinanceClientWS binanceClientWS = binanceClientWSMap.get(pairSymbol);
        if (binanceClientWS != null){
            return String.valueOf(binanceClientWS.getCurrentTradeTime());
        }
        return "-";
    }

    public Optional<BinanceClientWS> getBinanceClientWS(String pairSymbol) {
        return Optional.ofNullable(binanceClientWSMap.get(pairSymbol));
    }






}
