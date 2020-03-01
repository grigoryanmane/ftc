package aca.project.ftc;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories("aca.project.ftc.repository")
@EnableTransactionManagement
@EnableJpaAuditing
@Slf4j
public class FtcApplication {

	public static void main(String[] args) {
		SpringApplication.run(FtcApplication.class, args);
	}

}
