package fr.openclassrooms.rayane.trippricer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Locale;

@SpringBootApplication
@EnableEurekaClient
public class TripPricerApplication {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US", "WIN"));
        SpringApplication.run(TripPricerApplication.class, args);
    }

}
