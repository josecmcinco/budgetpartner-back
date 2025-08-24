package com.budgetpartner.APP.controller;

import com.budgetpartner.APP.service.DivisaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {

    @Autowired
    private DivisaService divisaService;


    @GetMapping("/convert")
    public double convert(
            @RequestParam double amount,
            @RequestParam String from,
            @RequestParam String to) {
        return divisaService.convertCurrency(amount, from, to);
    }
}