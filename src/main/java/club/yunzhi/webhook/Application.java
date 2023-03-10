package club.yunzhi.webhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication()
@EnableAsync
@EnableScheduling
@EnableJpaRepositories(value = "club.yunzhi.webhook")
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
