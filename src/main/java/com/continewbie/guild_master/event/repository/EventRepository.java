package com.continewbie.guild_master.event.repository;

import com.continewbie.guild_master.event.entity.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface EventRepository extends JpaRepository<Event, Long> {
    //쿼리 문제,, 점심

    @Query("SELECT e FROM Event e Join e.guild g on g.guildId = :guildId")
    Page<Event> findByGuildId(@Param("guildId") long guildId, Pageable pageable);

    @Query("SELECT e from Event e Join e.memberEvents me on me.member.memberId = :memberId")
    Page<Event> findByMemberId(@Param("memberId") long memberId, Pageable pageable);

    Optional<Event> findByMemberId(@Param("memberId") long memberId);

//    @Query("SELECT me FROM MemberEvent me Join me.event e on e.eventId = :eventId")
//    Page<MemberEvent> findByEventId(@Param("eventId") Long eventId, Pageable pageable);
//    Page<MemberEvent> findByEventId(Long eventId, Pageable pageable);
}
