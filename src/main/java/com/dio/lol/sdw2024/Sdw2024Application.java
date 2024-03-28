package com.dio.lol.sdw2024;

import com.dio.lol.sdw2024.application.AskChampionsUseCase;
import com.dio.lol.sdw2024.application.ListChampionsUseCase;
import com.dio.lol.sdw2024.domain.model.Champion;
import com.dio.lol.sdw2024.domain.ports.ChampionRepository;
import com.dio.lol.sdw2024.domain.ports.GenerativeAIAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@EnableFeignClients
@SpringBootApplication
public class Sdw2024Application {

	public static void main(String[] args) {
		SpringApplication.run(Sdw2024Application.class, args);
	}

	@Bean
	public ListChampionsUseCase provideListChampionsUseCase(ChampionRepository championRepository) {
		return new ListChampionsUseCase(championRepository);
	}

	@Bean
	public AskChampionsUseCase provideAskChampionsUseCase(ChampionRepository championRepository,
														  GenerativeAIAPI generativeAIAPI) {
		return new AskChampionsUseCase(championRepository, generativeAIAPI);
	}
}
