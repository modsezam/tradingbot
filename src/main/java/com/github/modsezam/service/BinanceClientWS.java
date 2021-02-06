package com.github.modsezam.service;

import com.github.modsezam.api.binance.BinanceApiWebSocketClient;
import com.github.modsezam.api.binance.domain.event.AggTradeEvent;
import com.google.common.collect.MapMaker;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class BinanceClientWS {

    private final Closeable closeableClientWS;

    @Getter
    private final long timeDifference = 5000L;

    @Getter
    private final String pairSymbol;
    @Getter
    private String currentPrice;
    @Getter
    private long currentTradeTime;
    @Getter
    private long tempTradeTime;

    @Getter
    private final ConcurrentMap<Long, AggTradeEvent> tradeEventQueue = new MapMaker().initialCapacity(360).makeMap();


    public BinanceClientWS(String pairSymbol, BinanceClientFactoryService binanceClientFactoryService) {
        this.pairSymbol = pairSymbol.toLowerCase();
        BinanceApiWebSocketClient clientWS = binanceClientFactoryService.getBinanceWSClient().newWebSocketClient();
        tempTradeTime = (System.currentTimeMillis() / 1000) * 1000;
        closeableClientWS = clientWS.onAggTradeEvent(this.pairSymbol, response -> {

            currentTradeTime = response.getEventTime();
            currentPrice = response.getPrice();
            if (currentTradeTime > tempTradeTime) {
                if (iterationAreMissed()) {
                    long missedIteration = (currentTradeTime - tempTradeTime) / timeDifference;

                    for (int i = 0; i < missedIteration; i++) {
                        tradeEventQueue.put(tempTradeTime + (i * timeDifference),
                                tradeEventQueue.getOrDefault(tempTradeTime - timeDifference, new AggTradeEvent()));
                        log.info("new tradeEventQueue tempTradeTime: {}, actualTradeDiffTime: (missed)",
                                tempTradeTime + (i * timeDifference));
                    }
                    long newLastTradeTime = tempTradeTime + (missedIteration * timeDifference);
                    tradeEventQueue.put(newLastTradeTime, response);
                    log.info("new tradeEventQueue tempTradeTime: {}, actualTradeDiffTime: {}",
                            newLastTradeTime, tradeEventQueue.get(newLastTradeTime).getTradeTime() - newLastTradeTime);
                    tempTradeTime = newLastTradeTime + timeDifference;

                } else {
                    tradeEventQueue.put(tempTradeTime, response);
                    log.info("new tradeEventQueue tempTradeTime: {}, actualTradeDiffTime: {} (main)",
                            tempTradeTime, tradeEventQueue.get(tempTradeTime).getTradeTime() - tempTradeTime);
                    tempTradeTime += timeDifference;
                }
            }
            log.debug("new event from pair {} - price {}", pairSymbol, response.getPrice());
        });

        log.info("create new Binance WS client - pair {}", pairSymbol);
    }

    private boolean iterationAreMissed() {
        return tempTradeTime + timeDifference < currentTradeTime;
    }

    private LocalDateTime convertLongToLocalDataTime(long price) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(price), TimeZone.getDefault().toZoneId());
    }

//    public Optional<AggTradeEvent> getLastMinutesPrice(Long minutes){
//        return Optional.ofNullable(tradeEventQueue.get(LocalDateTime.now().minusMinutes(minutes).withNano(0)));
//    }

    public Optional<AggTradeEvent> getLastSecondsPrice(Long seconds) {
        return Optional.ofNullable(tradeEventQueue.get(System.currentTimeMillis() / 1000 - seconds));
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
