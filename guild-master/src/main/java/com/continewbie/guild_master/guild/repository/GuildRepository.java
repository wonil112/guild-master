package com.continewbie.guild_master.guild.repository;

import com.continewbie.guild_master.guild.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GuildRepository extends JpaRepository<Guild, Long> {
    @Query("SELECT g FROM Guild g WHERE g.game.id = :gameId AND g.guildName = :guildName")
    Optional<Guild> findByGameIdAndGuildName(@Param("gameId") Long gameId, @Param("guildName") String guildName);


}
