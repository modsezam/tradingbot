package com.github.modsezam.api.binance.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum SwapRemoveType {
    SINGLE, COMBINATION
}
