package com.continewbie.guild_master.memeberevent.repository;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


public interface MemberEventRepository extends JpaRepository<MemberEvent, Long> {
//    Page<MemberEvent> findByEvent_EventId(Long id, Pageable pageable);
    Page<MemberEvent> findByEvent_EventId(Long eventId, Pageable pageable);
    Page<MemberEvent> findByEvent(Event event, Pageable pageable);

}
