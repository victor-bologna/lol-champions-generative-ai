package com.dio.lol.sdw2024.application;

import com.dio.lol.sdw2024.domain.exception.ChampionNotFoundException;
import com.dio.lol.sdw2024.domain.model.Champion;
import com.dio.lol.sdw2024.domain.ports.ChampionRepository;
import com.dio.lol.sdw2024.domain.ports.GenerativeAIAPI;

public record AskChampionsUseCase (ChampionRepository championRepository, GenerativeAIAPI generativeAIAPI) {
    public String askChampion(Long championId, String question) {
        Champion champion = championRepository.findById(championId)
                .orElseThrow(() -> new ChampionNotFoundException(championId));
        String championContext = champion.generateContextByQuestion(question);
        String objective = """
                Atue como um assistente com habilidade de se comportar como os Campões do League of Legends (LoL).
                Responda perguntas incorporando a personalidade e estilo de um determinado Campeão.
                Segue a pergunta, nome do Campeão e sua respectiva lore (história):
                 
                """;
        return generativeAIAPI.generateContent(objective, championContext);
    }
}
