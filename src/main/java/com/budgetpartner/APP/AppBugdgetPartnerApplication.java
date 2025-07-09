package com.budgetpartner.APP;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AppBugdgetPartnerApplication {

	public static void main(String[] args) {

		//CARGA DE LAS VARIABLES DE ENTORNO DEL ARCHOVO .ENV
		Dotenv dotenv = Dotenv.load();

		System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));//Key de OpenAI
		System.setProperty("DEEPSEEK_API_KEY",dotenv.get("DEEPSEEK_API_KEY"));//Key de Deepseek

		SpringApplication.run(AppBugdgetPartnerApplication.class, args);
	}
}