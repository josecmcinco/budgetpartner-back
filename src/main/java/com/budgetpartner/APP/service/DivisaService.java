package com.budgetpartner.APP.service;

import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;

@Service
public class DivisaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://api.frankfurter.app";

    // Conversión simple entre dos divisas
    public double convertCurrency(double amount, MonedasDisponibles from, MonedasDisponibles to) {
        String url = BASE_URL + "/latest?amount=" + amount + "&from=" + from.toString() + "&to=" + to.toString();

        System.out.println(url);
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        System.out.println("Hay respuesta");
        if (response != null && response.containsKey("rates")) {
            Map<String, Double> rates = (Map<String, Double>) response.get("rates");
            return rates.get(to.toString());
        }
        throw new NotFoundException("Error al convertir divisas");
    }
}
