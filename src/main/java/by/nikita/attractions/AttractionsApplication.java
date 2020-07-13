package by.nikita.attractions;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class AttractionsApplication {
	static {
		ApiContextInitializer.init();
	}

    public static void main(String[] args) {
        SpringApplication.run(AttractionsApplication.class, args);
    }
}
