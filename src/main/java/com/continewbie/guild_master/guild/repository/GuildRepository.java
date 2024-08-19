package com.continewbie.guild_master.guild.repository;

import com.continewbie.guild_master.guild.entity.Guild;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuildRepository extends JpaRepository<Guild, Long> {
}
