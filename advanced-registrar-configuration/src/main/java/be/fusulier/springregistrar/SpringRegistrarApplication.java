package be.fusulier.springregistrar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
@Slf4j
@EnableSpecialClassesScanning("be.fusulier.springregistrar")
public class SpringRegistrarApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringRegistrarApplication.class, args);
    }

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) {

        SpecialClass bean = applicationContext.getBean(SpecialClass.class);
        bean.hello();
    }
}
