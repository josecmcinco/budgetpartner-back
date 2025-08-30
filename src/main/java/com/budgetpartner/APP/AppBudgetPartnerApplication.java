package com.budgetpartner.APP;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class AppBudgetPartnerApplication {

	public static void main(String[] args) {

		//CARGA DE LAS VARIABLES DE ENTORNO DEL ARCHOVO .ENV

		Dotenv dotenv = Dotenv.configure().directory("C:/Users/josec/BudgetPartner").load();

		System.setProperty("OPENAI_API_KEY", Objects.requireNonNull(dotenv.get("OPENAI_API_KEY")));//Key de OpenAI
		System.setProperty("DEEPSEEK_API_KEY", Objects.requireNonNull(dotenv.get("DEEPSEEK_API_KEY")));//Key de Deepseek

		SpringApplication.run(AppBudgetPartnerApplication.class, args);
	}

}

