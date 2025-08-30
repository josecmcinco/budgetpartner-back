package com.budgetpartner.APP.service;

import com.budgetpartner.APP.dto.api.CurrencyResponse;
import com.budgetpartner.APP.enums.MonedasDisponibles;
import com.budgetpartner.APP.exceptions.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.Optional;


/**
 * Servicio encargado de realizar conversiones de divisas
 * Se conecta al endpoint: {@code https://api.frankfurter.app}
 * para obtener el valor actualizado entre monedas.
 */
@Service
public class DivisaService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BASE_URL = "https://api.frankfurter.app";


    /**
     * Convierte un importe de una divisa a otra utilizando
     * la API de Frankfurter.
     *
     * @param amount cantidad a convertir
     * @param from   divisa de origen (ejemplo: {@code USD}, {@code EUR})
     * @param to     divisa de destino (ejemplo: {@code GBP}, {@code JPY})
     * @return el valor convertido en la divisa destino
     * @throws NotFoundException si no se obtiene respuesta válida de la API
     */
    public double convertCurrency(double amount, MonedasDisponibles from, MonedasDisponibles to) {

        //Construcción de URL sin concatenar strings
        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(BASE_URL + "/latest")
                .queryParam("amount", amount)
                .queryParam("from", from)
                .queryParam("to", to);

        String url = builder.toUriString();

        CurrencyResponse response = restTemplate.getForObject(url, CurrencyResponse.class);
        return Optional.ofNullable(response)
                .map(CurrencyResponse::getRates)
                .map(r -> r.get(to.toString()))
                .orElseThrow(() -> new NotFoundException("Error al convertir divisas"));
    }
}
