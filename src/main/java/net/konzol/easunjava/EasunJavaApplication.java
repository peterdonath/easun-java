package net.konzol.easunjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class EasunJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(EasunJavaApplication.class, args);
	}

}
