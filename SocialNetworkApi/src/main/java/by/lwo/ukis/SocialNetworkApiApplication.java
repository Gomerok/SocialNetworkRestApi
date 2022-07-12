package by.lwo.ukis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class SocialNetworkApiApplication {

    public static void main(String[] args) {
        String[] strings = args.clone();


        SpringApplication.run(SocialNetworkApiApplication.class, args);
    }

}
