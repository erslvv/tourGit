package kz.safetrip.safetrip;

import kz.safetrip.safetrip.security.JwtProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class SafetripApplication {
	public static void main(String[] args) {
		SpringApplication.run(SafetripApplication.class, args);
	}
}
