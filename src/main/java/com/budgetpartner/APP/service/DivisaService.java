package com.budgetpartner.APP.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class DivisaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://api.frankfurter.app";

    // Conversión simple entre dos divisas
    public double convertCurrency(double amount, String from, String to) {
        String url = BASE_URL + "/latest?amount=" + amount + "&from=" + from + "&to=" + to;
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        if (response != null && response.containsKey("rates")) {
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            return rates.get(to);
        }
        throw new RuntimeException("Error al convertir divisas");
    }
}
