package stonker.stonker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class}) // turn off database auto config
public class StonkerApplication {

	public static void main(String[] args) {
		SpringApplication.run(StonkerApplication.class, args);
	}

}
