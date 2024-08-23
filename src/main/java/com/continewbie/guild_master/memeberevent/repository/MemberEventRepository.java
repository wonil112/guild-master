package com.continewbie.guild_master.memeberevent.repository;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MemberEventRepository extends JpaRepository<MemberEvent, Long> {

    @Query("SELECT me FROM MemberEvent me WHERE me.event.eventId = :eventId")
    Page<MemberEvent> findByEventId(long eventId, Pageable pageable);

    @Query("SELECT me FROM MemberEvent me WHERE me.event.eventId = :eventId")
    List<MemberEvent> findByEventId(long eventId);

}
