package com.continewbie.guild_master.event.service;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.event.repository.EventRepository;
import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    public EventService(EventRepository eventRepository, MemberRepository memberRepository) {
        this.eventRepository = eventRepository;
        this.memberRepository = memberRepository;
    }

    public Event createEvent(Event event) {
        return eventRepository.save(event);
    }

    public Event updateEvent(Event event) {
        Event findEvent = findVerifiedEvent(event.getEventId());

        Optional.ofNullable(event.getEventName())
                .ifPresent(eventName -> findEvent.setEventName(eventName));
        Optional.ofNullable(event.getEventContent())
                .ifPresent(eventContent -> findEvent.setEventContent(eventContent));
        Optional.ofNullable(event.getEventTotalPopulation())
                .ifPresent(eventTotalPopulation -> findEvent.setEventTotalPopulation(eventTotalPopulation));

        return eventRepository.save(findEvent);
    }

    public Event findEvent(long eventId){
        return findVerifiedEvent(eventId);
    }

//    public Page<Event> findEvents(long id){
//        return eventRepository.findAll(PageRequest
//                .of(page, size, Sort.by("eventId")));
//    }

    public void deleteEvent(long eventId){
        Event findEvent = findVerifiedEvent(eventId);
        eventRepository.delete(findEvent);
    }

    public Event findVerifiedEvent(long eventId){
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        Event findEvent = optionalEvent.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.EVENT_NOT_FOUND));
        return findEvent;
    }

}
