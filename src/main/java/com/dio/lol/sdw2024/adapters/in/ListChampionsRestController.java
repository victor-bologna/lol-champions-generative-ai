package com.dio.lol.sdw2024.adapters.in;

import com.dio.lol.sdw2024.application.ListChampionsUseCase;
import com.dio.lol.sdw2024.domain.model.Champion;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "Champions", description = "Return champions data")
@RestController
@RequestMapping("/champions")
public record ListChampionsRestController (ListChampionsUseCase listChampionsUseCase) {
    @CrossOrigin
    @GetMapping
    public List<Champion> findAllChampions() {
        return listChampionsUseCase.findAll();
    }
}
