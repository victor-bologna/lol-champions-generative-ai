package com.dio.lol.sdw2024.adapters.out;

import com.dio.lol.sdw2024.domain.ports.GenerativeAIAPI;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@ConditionalOnProperty(name = "generative-ai.provider", havingValue = "chat-gpt")
@FeignClient(name ="chatGPTAPI", url = "${chat-gpt.base-url}", configuration = OpenAIAPI.Config.class)
public interface OpenAIAPI extends GenerativeAIAPI {

    @PostMapping(path = "/v1/chat/completions")
    OpenAIResponse chatCompletion(OpenAIRequest openAIRequest);

    @Override
    default String generateContent(String objective, String context) {
        String model = "gpt-3.5-turbo";
        List<Message> messages = List.of(new Message("system", objective), new Message("user", context));
        OpenAIRequest request = new OpenAIRequest(model, messages);
        OpenAIResponse openAIResponse = chatCompletion(request);
        return openAIResponse.choices().getFirst().message().content();
    }

    record OpenAIRequest(String model, List<Message> messages) {}
    record Message(String role, String content) {}
    record OpenAIResponse(List<Choice> choices) {}
    record Choice(int index, Message message) {}

    class Config {
        @Bean
        public RequestInterceptor apiKeyRequestInterceptor(@Value("${chat-gpt.api-key}") String apiKey) {
            return requestTemplate -> requestTemplate.header(
                    HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(apiKey));
        }
    }
}
