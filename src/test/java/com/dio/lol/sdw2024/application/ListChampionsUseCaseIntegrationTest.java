package com.dio.lol.sdw2024.application;

import com.dio.lol.sdw2024.domain.model.Champion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ListChampionsUseCaseIntegrationTest {

    @Autowired
    private ListChampionsUseCase listChampionsUseCase;

    @Test
    void testFindAllChampions() {
        List<Champion> championList = listChampionsUseCase.findAll();

        assertEquals(12, championList.size());
    }

    @Test
    void championRepository() {
        Optional<Champion> champion = listChampionsUseCase.findById(1L);

        assertNotNull(champion);
        assertEquals(1L, champion.get().id());
    }
}