package tourGuide;

import feign.Feign;
import feign.gson.GsonDecoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tourGuide.proxies.TripPricerProxy;
import feign.gson.GsonEncoder;

import java.util.Locale;

@SpringBootApplication
@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        Locale.setDefault(new Locale("en", "US", "WIN"));
        SpringApplication.run(Application.class, args);
    }

}
