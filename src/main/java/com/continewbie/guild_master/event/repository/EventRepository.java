package com.continewbie.guild_master.event.repository;

import com.continewbie.guild_master.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Event, Long> {

}
