package com.kirana.finalphase1.service;

import com.kirana.finalphase1.cache.FxRateCacheService;
import com.kirana.finalphase1.dto.FxRateResponseDTO;
import com.kirana.finalphase1.enums.CurrencyType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * The type Currency conversion service.
 */
@Slf4j
@Service
public class CurrencyConversionService {

    private final FxRateCacheService fxRateCacheService;
    private final WebClient webClient;

    @Value("${fx.rate.cache.ttl.seconds}")
    private long ttlSeconds;

    /**
     * Instantiates a new Currency conversion service.
     *
     * @param fxRateCacheService the fx rate cache service
     * @param webClientBuilder   the web client builder
     */
    public CurrencyConversionService(
            FxRateCacheService fxRateCacheService,
            WebClient.Builder webClientBuilder) {

        this.fxRateCacheService = fxRateCacheService;
        this.webClient = webClientBuilder
                .baseUrl("https://api.fxratesapi.com")
                .build();
    }

    /**
     * UNIVERSAL conversion method
     *
     * @param amount       the amount
     * @param fromCurrency the from currency
     * @return the big decimal
     */
    public BigDecimal convertToINR(BigDecimal amount, CurrencyType fromCurrency) {

        if (fromCurrency == CurrencyType.INR) {
            return amount.setScale(2, RoundingMode.HALF_UP);
        }

        CurrencyType toCurrency = CurrencyType.INR;

        // CACHE CHECK
        BigDecimal cachedRate =
                fxRateCacheService.getRate(fromCurrency, toCurrency);

        if (cachedRate != null) {
            log.info("FX CACHE HIT → {} → {} rate={}",
                    fromCurrency, toCurrency, cachedRate);

            return amount.multiply(cachedRate)
                    .setScale(2, RoundingMode.HALF_UP);
        }

        log.warn("FX CACHE MISS → {} → {} (calling external API)",
                fromCurrency, toCurrency);

        // API CALL
        FxRateResponseDTO response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/latest")
                        .queryParam("base", fromCurrency.name())
                        .queryParam("symbols", toCurrency.name())
                        .build())
                .retrieve()
                .bodyToMono(FxRateResponseDTO.class)
                .block();

        if (response == null ||
                response.getRates() == null ||
                response.getRates().get(toCurrency.name()) == null) {

            throw new RuntimeException("Failed to fetch FX rate");
        }

        BigDecimal rate = BigDecimal.valueOf(
                response.getRates().get(toCurrency.name())
        );

        // SAVE TO CACHE
        fxRateCacheService.saveRate(
                fromCurrency,
                toCurrency,
                rate,
                ttlSeconds
        );

        return amount.multiply(rate)
                .setScale(2, RoundingMode.HALF_UP);
    }
}
