package com.dio.lol.sdw2024.application;

import com.dio.lol.sdw2024.domain.model.Champion;
import com.dio.lol.sdw2024.domain.ports.ChampionRepository;

import java.util.List;
import java.util.Optional;

public record ListChampionsUseCase(ChampionRepository championRepository) {
    public List<Champion> findAll() {
        return championRepository.findAll();
    }

    public Optional<Champion> findById(long id) {
        return championRepository.findById(id);
    }
}
