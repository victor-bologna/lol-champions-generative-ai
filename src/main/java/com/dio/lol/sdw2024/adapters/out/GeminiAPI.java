package com.dio.lol.sdw2024.adapters.out;

import com.dio.lol.sdw2024.domain.ports.GenerativeAIAPI;
import feign.FeignException;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "gemini", matchIfMissing = true)
@FeignClient(name ="geminiAPI", url = "${gemini.base-url}", configuration = GeminiAPI.Config.class)
public interface GeminiAPI extends GenerativeAIAPI {

    @PostMapping(path = "/v1beta/models/gemini-pro:generateContent")
    GeminiResponse textOnlyInput(GeminiRequest openAIRequest);

    @Override
    default String generateContent(String objective, String context) {
        String prompt = """
                %s
                %s""".formatted(objective, context);
        GeminiRequest geminiRequest = new GeminiRequest(
                List.of(new Content(
                        List.of(new Part(prompt)))));
        try {
            GeminiResponse geminiResponse = textOnlyInput(geminiRequest);
            return geminiResponse.candidates().getFirst().content().parts().getFirst().text();
        } catch (FeignException feignException) {
            feignException.printStackTrace();
            return "Erro de comunicação com Google Gemini.";
        } catch (Exception exception) {
            exception.printStackTrace();
            return "O retorno da API Google Gemini não contem os dados esperados.";
        }
    }

    record GeminiRequest(List<Content> contents) {}
    record Content(List<Part> parts) {}
    record Part(String text) {}
    record GeminiResponse(List<Candidate> candidates) {}
    record Candidate(Content content) {}

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${gemini.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.query(
                    "key", apiKey);
        }
    }
}
