package com.acciojobsshopping.backend.Accio.shopping.Website.Backend;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Accio Shopping Website Documentation",description = "Shopping Website Backend",version = "2.0",contact=@Contact(name = "Akash More", url="https://github.com/Akash-more1",email = "akashmore9725@gmail.com")))
public class AccioShoppingWebsiteBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccioShoppingWebsiteBackendApplication.class, args);
	}

}
