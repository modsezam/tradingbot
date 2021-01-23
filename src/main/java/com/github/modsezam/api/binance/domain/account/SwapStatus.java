package com.github.modsezam.api.binance.domain.account;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum SwapStatus {
    PENDING,
    SUCCESS,
    FAILED;

    @JsonValue
    public int toValue() {
        return ordinal();
    }
}


