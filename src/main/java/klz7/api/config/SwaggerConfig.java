package klz7.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class SwaggerConfig { // Config da OpenApi swagger
	
	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("API de Locadora")
						.version("1.0.0")
						.description("API para gerenciamento de locadora de filmes.\n" +
									 "Aqui você pode gerenciar clientes, filmes, locações, etc.")
						.contact(new Contact()
								.name("Kleber Luiz")
								.email("kleberluizf15@gmail.com")
								.url("https://www.linkedin.com/in/kleberluizferreiramachado"))
						.license(new License()
								.name("Apache 2.0")
								.url("https://www.apache.org/licenses/LICENSE-2.0.html")))
				.externalDocs(new ExternalDocumentation()
						.description("Documentação detalhada")
						.url("https://github.com/kleberlz17/api_locadora"));
				
						


	}

}
