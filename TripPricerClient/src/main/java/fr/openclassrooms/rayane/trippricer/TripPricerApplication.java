package fr.openclassrooms.rayane.trippricer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class TripPricerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TripPricerApplication.class, args);
    }

}
