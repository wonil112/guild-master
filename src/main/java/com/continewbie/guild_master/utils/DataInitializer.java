package com.continewbie.guild_master.utils;

import com.continewbie.guild_master.game.entity.Game;
import com.continewbie.guild_master.game.repository.GameRepository;
import com.continewbie.guild_master.position.entity.Position;
import com.continewbie.guild_master.position.repository.PositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;


@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PositionRepository positionRepository;

    @Transactional
    @Override
    public void run(String... args) throws Exception {
        LocalDateTime now = LocalDateTime.now();

        Game overwatch = new Game(1L, "Overwatch", "OVW");
        Game valorant = new Game(2L, "Valorant", "VAL");
        Game leagueOfLegends = new Game(3L, "LeagueOfLegends", "LOL");
        Game lostArk = new Game(4L, "LostArk", "LOA");

        gameRepository.saveAll(List.of(overwatch, valorant, leagueOfLegends, lostArk));

        List<Position> overwatchPositions = List.of(
                new Position("Tanker", "OVW", overwatch),
                new Position("Dealer", "OVW", overwatch),
                new Position("Healer", "OVW", overwatch),
                new Position("Anything", "OVW", overwatch)
        );

        List<Position> valorantPositions = List.of(
                new Position("Brimstone", "VAL", valorant),
                new Position("Phoenix", "VAL", valorant),
                new Position("Sage", "VAL", valorant),
                new Position("Sova", "VAL", valorant),
                new Position("Viper", "VAL", valorant),
                new Position("Cypher", "VAL", valorant),
                new Position("Reyna", "VAL", valorant),
                new Position("Killjoy", "VAL", valorant),
                new Position("Breach", "VAL", valorant),
                new Position("Omen", "VAL", valorant),
                new Position("Jett", "VAL", valorant),
                new Position("Raze", "VAL", valorant),
                new Position("Skye", "VAL", valorant),
                new Position("Yoru", "VAL", valorant),
                new Position("Astra", "VAL", valorant),
                new Position("Kayo", "VAL", valorant),
                new Position("Chamber", "VAL", valorant),
                new Position("Neon", "VAL", valorant),
                new Position("Fade", "VAL", valorant),
                new Position("Harbor", "VAL", valorant),
                new Position("Gekko", "VAL", valorant),
                new Position("Deadlock", "VAL", valorant),
                new Position("Iso", "VAL", valorant),
                new Position("Clove", "VAL", valorant)
        );

        List<Position> leagueOfLegendsPositions = List.of(
                new Position("Top", "LOL", leagueOfLegends),
                new Position("Jungle", "LOL", leagueOfLegends),
                new Position("Mid", "LOL", leagueOfLegends),
                new Position("Bottom", "LOL", leagueOfLegends),
                new Position("Support", "LOL", leagueOfLegends),
                new Position("Anything", "LOL", leagueOfLegends)
        );

        List<Position> lostArkPositions = List.of(
                new Position("Destroyer", "LOA", lostArk),
                new Position("Warlord", "LOA", lostArk),
                new Position("Berserker", "LOA", lostArk),
                new Position("HolyKnight", "LOA", lostArk),
                new Position("Slayer", "LOA", lostArk),
                new Position("Striker", "LOA", lostArk),
                new Position("Breaker", "LOA", lostArk),
                new Position("BattleMaster", "LOA", lostArk),
                new Position("InFighter", "LOA", lostArk),
                new Position("SoulMaster", "LOA", lostArk),
                new Position("LanceMaster", "LOA", lostArk),
                new Position("DevilHunter", "LOA", lostArk),
                new Position("Blaster", "LOA", lostArk),
                new Position("Hawkeye", "LOA", lostArk),
                new Position("Scouter", "LOA", lostArk),
                new Position("GunSlinger", "LOA", lostArk),
                new Position("Bard", "LOA", lostArk),
                new Position("Summoner", "LOA", lostArk),
                new Position("Arcana", "LOA", lostArk),
                new Position("Sorceress", "LOA", lostArk),
                new Position("Blade", "LOA", lostArk),
                new Position("Demonic", "LOA", lostArk),
                new Position("Reaper", "LOA", lostArk),
                new Position("SoulEater", "LOA", lostArk),
                new Position("Artist", "LOA", lostArk),
                new Position("AeroMancer", "LOA", lostArk)
        );

        // 포지션 데이터 저장
        positionRepository.saveAll(overwatchPositions);
        positionRepository.saveAll(valorantPositions);
        positionRepository.saveAll(leagueOfLegendsPositions);
        positionRepository.saveAll(lostArkPositions);
    }
}
