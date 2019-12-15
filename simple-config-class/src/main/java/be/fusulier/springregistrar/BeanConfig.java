package be.fusulier.springregistrar;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class BeanConfig {

    @Bean
    SimpleBean simpleBean() {
        return new SimpleBean();
    }
}
