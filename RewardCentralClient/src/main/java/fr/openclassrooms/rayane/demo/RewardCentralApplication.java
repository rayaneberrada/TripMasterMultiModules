package fr.openclassrooms.rayane.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RewardCentralApplication {

    public static void main(String[] args) {
        SpringApplication.run(RewardCentralApplication.class, args);
    }

}
