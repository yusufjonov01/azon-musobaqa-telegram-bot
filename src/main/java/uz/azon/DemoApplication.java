package uz.azon;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;
import uz.azon.config.InitConfig;
import uz.azon.entity.User;
import uz.azon.repository.UserRepository;

@EnableScheduling
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        if (InitConfig.isStart())
        SpringApplication.run(DemoApplication.class, args);
    }

}
