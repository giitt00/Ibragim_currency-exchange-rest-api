package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeRequestDto {

    private String baseCurrencyCode;

    private String targetCurrencyCode;

    private Double amount;
}
