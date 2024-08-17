package com.continewbie.guild_master.game.repository;

import com.continewbie.guild_master.game.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game,Long> {
}
