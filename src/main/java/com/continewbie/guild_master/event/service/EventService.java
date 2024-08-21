package com.continewbie.guild_master.event.service;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import com.continewbie.guild_master.event.repository.EventRepository;
import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.utils.validator.InvalidEventDateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;

    }

    public Event createEvent(Event event) {
        if (event.getStartDate().isAfter(event.getDueDate())) {
            throw new InvalidEventDateException(404, "종료 날짜는 시작 날짜보다 뒤여야 합니다");
        }
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

    public Page<Event> findEvents(int page, int size){
        return eventRepository.findAll(PageRequest.of(page, size,
                Sort.by("eventId").ascending()));
    }

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

    // 멤버와 게임티어, 선택포지션을 입력 받는 메서드
    public MemberEvent createAttendee (long eventId, MemberEvent memberEvent){

        Event findEvent = findVerifiedEvent(eventId);
        verifiedPopulation(findEvent);

        findEvent.setEventCurrentPopulation(findEvent.getEventCurrentPopulation() + 1);
        List<MemberEvent> findMemberEvents = findEvent.getMemberEvents();
        findMemberEvents.add(memberEvent);
        eventRepository.save(findEvent);
        return memberEvent;
    }

    //특정 이벤트의 참가자 삭제
    public void deleteAttendee (long eventId, long memberEventId){
        Optional<Event> event = eventRepository.findByEventId(eventId);
        if (event.isPresent()) {
            Event findEvent = event.get();

            // MemberEvent를 찾기
            Optional<MemberEvent> memberEventToDelete = findEvent.getMemberEvents().stream()
                    .filter(memberEvent -> memberEvent.getMemberEventId() == memberEventId)
                    .findFirst();

            if (memberEventToDelete.isPresent()) {
                // MemberEvent를 Event에서 삭제
                findEvent.getMemberEvents().remove(memberEventToDelete.get());

                // Event 저장
                eventRepository.save(findEvent);
            } else{
            throw new BusinessLogicException(ExceptionCode.EVENT_NOT_FOUND);
            }
        }
    }

    //현재 인원수 == 전체 인원수 인데 추가를 하려고 할 때 예외
    public void verifiedPopulation(Event event){

        if(event.getEventCurrentPopulation() == event.getEventTotalPopulation()){
            throw new BusinessLogicException(ExceptionCode.EVENT_MAX_PARTICIPANTS);
        }
    }

}
