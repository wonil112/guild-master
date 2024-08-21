package com.continewbie.guild_master.event.repository;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventId(Long eventId);
//    Page<MemberEvent> findByEvent_EventId(Long eventId, Pageable pageable);

}
