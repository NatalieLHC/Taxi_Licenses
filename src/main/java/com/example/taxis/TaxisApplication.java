package com.example.taxis;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(
        info = @Info(
                title = "Taxi Service",
                version = "v1.0",
                description = "Add vehicles to Exception List"

        )
)
@SpringBootApplication
public class TaxisApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxisApplication.class, args);
    }

}
