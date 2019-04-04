package ie.my353.inis;

import ie.my353.inis.service.SlotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableCaching
@EnableScheduling
public class InisApplication {


    public static void main(String[] args) {


        SpringApplication.run(InisApplication.class, args);


    }

}
