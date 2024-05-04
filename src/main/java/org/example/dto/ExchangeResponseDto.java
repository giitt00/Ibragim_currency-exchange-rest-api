package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResponseDto {

    private CurrencyResponseDto baseCurrency;

    private CurrencyResponseDto targetCurrency;

    private BigDecimal rate;

    private BigDecimal amount;

    private BigDecimal convertedAmount;
}
