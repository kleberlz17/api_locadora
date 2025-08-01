package klz7.api.config;

import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.annotation.PostConstruct;


//Configuraçaõ adicional para o Jackson
//Necessária porque os testes estavam falhando ao serializar/desserializar
//tipos de data como LocalDate.
@Configuration
public class JacksonConfig {
	
	private final ObjectMapper objectMapper;

	public JacksonConfig(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
	
	@PostConstruct
	public void registerModules() {
		objectMapper.registerModule(new JavaTimeModule());
	}
	
	
}
