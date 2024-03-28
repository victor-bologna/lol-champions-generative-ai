package com.dio.lol.sdw2024.adapters.in;

import com.dio.lol.sdw2024.application.AskChampionsUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Champions", description = "Return champions data")
@RestController
@RequestMapping("/champions")
public record AskChampionsRestController(AskChampionsUseCase askChampionsUseCase) {
    @PostMapping("/{championId}/ask")
    public AskChampionResponse askChampion(
            @PathVariable Long championId,
            @RequestBody AskChampionRequest askChampionRequest) {
        String answer = askChampionsUseCase.askChampion(championId, askChampionRequest.question());
        return new AskChampionResponse(answer);
    }

    public record AskChampionRequest(String question) { }
    public record AskChampionResponse(String answer) { }
}
