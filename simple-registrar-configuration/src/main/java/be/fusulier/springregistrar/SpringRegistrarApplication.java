package be.fusulier.springregistrar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Slf4j
@Import(BeanDefinitionRegistrar.class)
public class SpringRegistrarApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringRegistrarApplication.class, args);
    }

    @Autowired
    ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        SimpleBean simpleBean = applicationContext.getBean(SimpleBean.class);
        simpleBean.hello();
    }
}
