package com.dio.lol.sdw2024.domain.ports;

import com.dio.lol.sdw2024.domain.model.Champion;

import java.util.List;
import java.util.Optional;

public interface ChampionRepository {

    List<Champion> findAll();
    Optional<Champion> findById(long id);
}
