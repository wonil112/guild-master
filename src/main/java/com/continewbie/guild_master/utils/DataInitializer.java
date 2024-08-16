package com.continewbie.guild_master.utils;

import com.continewbie.guild_master.game.entity.Game;
import com.continewbie.guild_master.game.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private GameRepository gameRepository;


    @Transactional
    @Override
    public void run(String... args) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        // 게임 데이터 저장
        List<Game> gameList = List.of(
                new Game(1L, "Overwatch", "OVW", now, now, null),
                new Game(2L, "Valorant", "VAL", now, now, null),
                new Game(3L, "LeagueOfLegends", "LOL", now, now, null),
                new Game(4L, "LostArk", "LOA", now, now, null)
        );
        gameRepository.saveAll(gameList);

    }
}

