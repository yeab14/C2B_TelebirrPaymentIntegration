package com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service;

// import me.paulschwarz.springdotenv.annotation.DotenvPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.repository")
@EntityScan("com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service.entity")
@ComponentScan(basePackages = {"com.C2B_TelebirrPaymentIntegration.Payment_Integration_Service"})
@PropertySource(value = "classpath:.env", ignoreResourceNotFound = true) 
public class PaymentIntegrationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(PaymentIntegrationServiceApplication.class, args);
    }
}
