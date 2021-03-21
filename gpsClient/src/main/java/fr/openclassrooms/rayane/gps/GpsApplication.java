package fr.openclassrooms.rayane.gps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Locale;

@SpringBootApplication
@EnableEurekaClient
public class GpsApplication {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US", "WIN"));
        SpringApplication.run(GpsApplication.class, args);
    }

}
