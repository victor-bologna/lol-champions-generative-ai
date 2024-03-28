package com.dio.lol.sdw2024.adapters.out;

import com.dio.lol.sdw2024.domain.model.Champion;
import com.dio.lol.sdw2024.domain.ports.ChampionRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ChampionsJdbcRepository implements ChampionRepository {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Champion> rowMapper;

    public ChampionsJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.rowMapper = (rs, rowNum) ->new Champion(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getString("role"),
                rs.getString("lore"),
                rs.getString("image_url")
        );
    }

    @Override
    public List<Champion> findAll() {
        return jdbcTemplate.query("SELECT * FROM CHAMPIONS ", rowMapper);
    }

    @Override
    public Optional<Champion> findById(long id) {
        String query = "SELECT * FROM CHAMPIONS WHERE id = ?";
        List<Champion> champion = jdbcTemplate.query(query, rowMapper, id);
        return champion.stream().findFirst();
    }
}
