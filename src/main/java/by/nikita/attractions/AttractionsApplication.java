package by.nikita.attractions;

import by.nikita.attractions.entity.Bot;
import com.google.inject.internal.asm.$ConstantDynamic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

@SpringBootApplication
public class AttractionsApplication {
	static {
		ApiContextInitializer.init();
	}

    public static void main(String[] args) {
        SpringApplication.run(AttractionsApplication.class, args);
    }
}
