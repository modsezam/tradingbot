package com.github.modsezam.service;

import com.github.modsezam.api.binance.BinanceApiRestClient;
import com.github.modsezam.api.binance.domain.account.Account;
import com.github.modsezam.api.binance.domain.market.Candlestick;
import com.github.modsezam.api.binance.domain.market.CandlestickInterval;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class BinanceClient {

    private final BinanceApiRestClient client;

    public BinanceClient(BinanceClientFactoryService binanceClientFactoryService) {
        client = binanceClientFactoryService.getBinanceClient().newRestClient();
    }

    public Account getAccount() {
//
//        List<Trade> myTrades = client.getMyTrades("BTCUSDT", 20);
//        for (Trade trade : myTrades) {
//            long time = trade.getTime();
//            System.out.println("trade.getSymbol() = " + trade.getSymbol());
//            System.out.println("new Date(time).toString() = " + new Date(time).toString());
//            System.out.println("trade.getPrice() = " + trade.getPrice());
//            System.out.println("trade.getQty() = " + trade.getQty());
//            System.out.println("trade.getQuoteQty() = " + trade.getQuoteQty());
//            System.out.println("trade.getCommission() = " + trade.getCommission());
//            System.out.println("trade.getCommissionAsset() = " + trade.getCommissionAsset());
//            System.out.println("--");
//        }
//
//        AllOrdersRequest allOrdersRequest = new AllOrdersRequest("BTCUSDT");
//        List<Order> allOrders = client.getAllOrders(allOrdersRequest);
//        for (Order order : allOrders) {
//            System.out.println("new Date(order.getTime()).toString() = " + new Date(order.getTime()).toString());
//            System.out.println("order.getPrice() = " + order.getPrice());
//            System.out.println("order.getStatus() = " + order.getStatus());
//        }
//
//        Account account = client.getAccount();
////        System.out.println(account.getBalances());
//        System.out.println(account.getAssetBalance("BTC").getFree());
//        System.out.println(account.getAssetBalance("BTC").getLocked());
//        System.out.println("account.getBuyerCommission() = " + account.getBuyerCommission());
//        System.out.println("account.getMakerCommission() = " + account.getMakerCommission());
//        System.out.println("account.getSellerCommission() = " + account.getSellerCommission());
//        System.out.println("account.getTakerCommission() = " + account.getTakerCommission());

        List<Candlestick> candlestickBars = client.getCandlestickBars("BTCUSDT", CandlestickInterval.ONE_MINUTE);
        System.out.println("candlestickBars.size() = " + candlestickBars.size());
        for (Candlestick candlestickBar : candlestickBars) {
            System.out.println("candlestickBar.getClose() = " + candlestickBar.getClose());
            System.out.println("new Date(candlestickBar.getCloseTime()) = " + new Date(candlestickBar.getCloseTime()));
            System.out.println("--");
        }


        return client.getAccount();
    }

}
