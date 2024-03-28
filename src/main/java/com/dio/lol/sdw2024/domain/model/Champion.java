package com.dio.lol.sdw2024.domain.model;

public record Champion(long id, String name, String role, String lore, String imageUrl) {
    public String generateContextByQuestion(String question) {
        return """
                Pergunta: %s
                Nome do campeão: %s
                Função: %s
                Lore (História): %s
                """.formatted(question, this.name, this.role, this.lore);
    }
}
