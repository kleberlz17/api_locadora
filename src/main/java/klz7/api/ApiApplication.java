package klz7.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
public class ApiApplication {

	public static void main(String[] args) {
		// Carrega o .env localmente com as variaveis de ambiente
		Dotenv dotenv = Dotenv.configure()
				.filename(".env")
				.load();
		
		//Variaveis como propriedades do sistema
		System.setProperty("DB_URL", dotenv.get("DB_URL"));
		System.setProperty("DB_USERNAME", dotenv.get("DB_USERNAME"));
		System.setProperty("DB_PASSWORD", dotenv.get("DB_PASSWORD"));
		
		SpringApplication.run(ApiApplication.class, args);
	}

}
