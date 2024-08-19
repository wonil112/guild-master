package com.continewbie.guild_master.position.repository;

import com.continewbie.guild_master.position.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository extends JpaRepository<Position, Long> {
}
