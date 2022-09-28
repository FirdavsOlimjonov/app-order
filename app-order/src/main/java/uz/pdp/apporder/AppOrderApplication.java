package uz.pdp.apporder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableEurekaClient
@EntityScan(basePackages = {
        "uz.pdp.appproduct.entity",
        "uz.pdp.telegrambot.entity",
        "uz.pdp.apporder.entity",
})
@EnableJpaRepositories(basePackages = {
        "uz.pdp.apporder.repository"
})
public class AppOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppOrderApplication.class, args);
    }

}
