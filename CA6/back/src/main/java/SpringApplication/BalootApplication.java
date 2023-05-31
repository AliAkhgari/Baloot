package SpringApplication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"controllers", "services", "repositories"})
@EnableJpaRepositories(basePackages = "repositories")
@EntityScan(basePackages = "entities")
public class BalootApplication {
    public static void main(String[] args) {
        SpringApplication.run(BalootApplication.class, args);
    }
}
