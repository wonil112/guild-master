package com.continewbie.guild_master.event.controller;

import com.continewbie.guild_master.dto.MultiResponseDto;
import com.continewbie.guild_master.event.dto.EventDto;
import com.continewbie.guild_master.memeberevent.dto.MemberEventDto;
import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import com.continewbie.guild_master.event.mapper.EventMapper;
import com.continewbie.guild_master.memeberevent.mapper.MemberEventMapper;
import com.continewbie.guild_master.event.service.EventService;
import com.continewbie.guild_master.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/events")
@Validated
@Slf4j
public class EventController {
    private final static String DEFAULT_EVENT_URL = "/events";

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final MemberEventMapper memberEventMapper;



    public EventController(EventService eventService, EventMapper eventMapper, MemberEventMapper memberEventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.memberEventMapper = memberEventMapper;
    }

    @PostMapping
    public ResponseEntity postEvent(@Valid @RequestBody EventDto.Post requestBody) {
        Event event = eventService.createEvent(eventMapper.eventPostDtoToEvent(requestBody));

        URI location = UriCreator.createUri(DEFAULT_EVENT_URL, event.getEventId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{event-id}")
    public ResponseEntity patchEvent(@PathVariable("event-id") @Positive long eventId, @Valid @RequestBody EventDto.Patch requestBody) {
        requestBody.setEventId(eventId);
        Event event = eventMapper.eventPatchDtoToEvent(requestBody);
        Event updatedEvent = eventService.updateEvent(event);
        return new ResponseEntity<>(eventMapper.eventToEventResponseDto(updatedEvent), HttpStatus.OK);
    }

    @DeleteMapping("/{event-id}")
    public ResponseEntity deleteEvent(@PathVariable("event-id") @Positive long eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{event-id}")
    public ResponseEntity getEvent(@PathVariable("event-id") @Positive long eventId) {
        Event event = eventService.findEvent(eventId);
        return new ResponseEntity<>(eventMapper.eventToEventResponseDto(event), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getEvents(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size) {
        Page<Event> pageEvents = eventService.findEvents(page - 1, size);
        List<Event> findEvents = pageEvents.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(eventMapper.eventsToEventResponses(findEvents),
                        pageEvents), HttpStatus.OK);
    }

    //registration: 특정 이벤트를 참여하는 기능
    @PostMapping("/{event-id}/registration")
    public ResponseEntity postAttendee(@PathVariable("event-id") @Positive long eventId,
                                       @Valid @RequestBody MemberEventDto requestBody) {
        //MemberEventDto >> entity

        MemberEvent memberEvent = eventService.createAttendee(eventId, memberEventMapper.MemberEventDtoToMemberEvent(requestBody));

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //특정 이벤트 전체 참가자 조회하는 기능


    //이벤트 참가 신청 삭제
    @DeleteMapping("/{event-id}/{memberEvent-id}")
    public ResponseEntity deleteAttendees(@PathVariable("event-id") @Positive long eventId,
                                          @PathVariable("memberEvent-id") @Positive long memberEventId) {
        eventService.deleteAttendee(eventId, memberEventId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
