package com.github.modsezam.api.binance.impl;

import com.github.modsezam.api.binance.BinanceApiAsyncMarginRestClient;
import com.github.modsezam.api.binance.BinanceApiCallback;
import com.github.modsezam.api.binance.constant.BinanceApiConstants;
import com.github.modsezam.api.binance.domain.TransferType;
import com.github.modsezam.api.binance.domain.account.*;
import com.github.modsezam.api.binance.domain.account.request.CancelOrderRequest;
import com.github.modsezam.api.binance.domain.account.request.CancelOrderResponse;
import com.github.modsezam.api.binance.domain.account.request.OrderRequest;
import com.github.modsezam.api.binance.domain.account.request.OrderStatusRequest;
import com.github.modsezam.api.binance.domain.event.ListenKey;

import java.util.List;

import static com.github.modsezam.api.binance.impl.BinanceApiServiceGenerator.createService;

/**
 * Implementation of Binance's Margin REST API using Retrofit with asynchronous/non-blocking method calls.
 */
public class BinanceApiAsyncMarginRestClientImpl implements BinanceApiAsyncMarginRestClient {

    private final BinanceApiService binanceApiService;

    public BinanceApiAsyncMarginRestClientImpl(String apiKey, String secret) {
        binanceApiService = createService(BinanceApiService.class, apiKey, secret);
    }

    // Margin Account endpoints

    @Override
    public void getAccount(Long recvWindow, Long timestamp, BinanceApiCallback<MarginAccount> callback) {
        binanceApiService.getMarginAccount(recvWindow, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getAccount(BinanceApiCallback<MarginAccount> callback) {
        long timestamp = System.currentTimeMillis();
        binanceApiService.getMarginAccount(BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getOpenOrders(OrderRequest orderRequest, BinanceApiCallback<List<Order>> callback) {
        binanceApiService.getOpenMarginOrders(orderRequest.getSymbol(), orderRequest.getRecvWindow(),
                orderRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void newOrder(MarginNewOrder order, BinanceApiCallback<MarginNewOrderResponse> callback) {
        binanceApiService.newMarginOrder(order.getSymbol(), order.getSide(), order.getType(), order.getTimeInForce(),
                order.getQuantity(), order.getPrice(), order.getNewClientOrderId(), order.getStopPrice(), order.getIcebergQty(),
                order.getNewOrderRespType(), order.getSideEffectType(), order.getRecvWindow(), order.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void cancelOrder(CancelOrderRequest cancelOrderRequest, BinanceApiCallback<CancelOrderResponse> callback) {
        binanceApiService.cancelMarginOrder(cancelOrderRequest.getSymbol(),
                cancelOrderRequest.getOrderId(), cancelOrderRequest.getOrigClientOrderId(), cancelOrderRequest.getNewClientOrderId(),
                cancelOrderRequest.getRecvWindow(), cancelOrderRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getOrderStatus(OrderStatusRequest orderStatusRequest, BinanceApiCallback<Order> callback) {
        binanceApiService.getMarginOrderStatus(orderStatusRequest.getSymbol(),
                orderStatusRequest.getOrderId(), orderStatusRequest.getOrigClientOrderId(),
                orderStatusRequest.getRecvWindow(), orderStatusRequest.getTimestamp()).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void getMyTrades(String symbol, BinanceApiCallback<List<Trade>> callback) {
        binanceApiService.getMyTrades(symbol, null, null, BinanceApiConstants.DEFAULT_RECEIVING_WINDOW, System.currentTimeMillis()).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    // user stream endpoints

    @Override
    public void startUserDataStream(BinanceApiCallback<ListenKey> callback) {
        binanceApiService.startMarginUserDataStream().enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void keepAliveUserDataStream(String listenKey, BinanceApiCallback<Void> callback) {
        binanceApiService.keepAliveMarginUserDataStream(listenKey).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void transfer(String asset, String amount, TransferType type, BinanceApiCallback<MarginTransaction> callback) {
        long timestamp = System.currentTimeMillis();
        binanceApiService.transfer(asset, amount, type.getValue(), BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void borrow(String asset, String amount, BinanceApiCallback<MarginTransaction> callback) {
        long timestamp = System.currentTimeMillis();
        binanceApiService.borrow(asset, amount, BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }

    @Override
    public void repay(String asset, String amount, BinanceApiCallback<MarginTransaction> callback) {
        long timestamp = System.currentTimeMillis();
        binanceApiService.repay(asset, amount, BinanceApiConstants.DEFAULT_MARGIN_RECEIVING_WINDOW, timestamp).enqueue(new BinanceApiCallbackAdapter<>(callback));
    }
}
