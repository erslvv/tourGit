package kz.safetrip.safetrip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("kz.safetrip.safetrip.repository")
@EntityScan("kz.safetrip.safetrip.model.entity")
public class SafetripApplication {
	public static void main(String[] args) {
		SpringApplication.run(SafetripApplication.class, args);
	}
}
