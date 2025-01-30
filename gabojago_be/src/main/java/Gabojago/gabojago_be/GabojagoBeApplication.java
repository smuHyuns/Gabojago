package Gabojago.gabojago_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GabojagoBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(GabojagoBeApplication.class, args);
    }

}
