package com.kirana.finalphase1.dto;

import lombok.Data;

import java.util.Map;
@Data
public class FxRateResponseDTO {

    private String base;
    private Map<String, Double> rates;


}
