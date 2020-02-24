package aca.project.ftc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("aca.project.ftc.repository")
@EnableTransactionManagement
@EnableJpaAuditing
public class FtcApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtcApplication.class, args);

	}

}
