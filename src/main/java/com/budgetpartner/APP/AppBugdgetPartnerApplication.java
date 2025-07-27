package com.budgetpartner.APP;

import com.budgetpartner.APP.repository.UsuarioRepository;
import com.budgetpartner.APP.service.MiembroService;
import com.budgetpartner.APP.service.UsuarioService;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AppBugdgetPartnerApplication {

	public static void main(String[] args) {

		//CARGA DE LAS VARIABLES DE ENTORNO DEL ARCHOVO .ENV

		//String apiKey = System.getenv("OPENAI_API_KEY");
		Dotenv dotenv = Dotenv.configure().directory("C:/Users/josec/BudgetPartner").load();

		System.setProperty("OPENAI_API_KEY", dotenv.get("OPENAI_API_KEY"));//Key de OpenAI
		System.setProperty("DEEPSEEK_API_KEY",dotenv.get("DEEPSEEK_API_KEY"));//Key de Deepseek
		//System.setProperty("DEEPSEEK_API_KEY",dotenv.get("_API_KEY"));//Key de Deepseek

		SpringApplication.run(AppBugdgetPartnerApplication.class, args);
	}

}

