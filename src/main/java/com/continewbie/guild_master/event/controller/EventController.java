package com.continewbie.guild_master.event.controller;

import com.continewbie.guild_master.dto.MultiResponseDto;
import com.continewbie.guild_master.event.dto.EventDto;
import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.event.mapper.EventMapper;
import com.continewbie.guild_master.event.repository.EventRepository;
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

@RestController
@RequestMapping("/events")
@Validated
@Slf4j
public class EventController {
    private final static String DEFAULT_EVENT_URL = "/events";

    private final EventService eventService;
    private final EventMapper eventMapper;

    public EventController(EventService eventService, EventMapper eventMapper) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
    }

    @PostMapping
    public ResponseEntity postEvent(@Valid @RequestBody EventDto.Post requestBody) {
        Event event = eventMapper.eventPostDtoToEvent(requestBody);
        Event createdEvent = eventService.createEvent(event);
        URI location = UriCreator.createUri(DEFAULT_EVENT_URL, createdEvent.getEventId());
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
        Page<Event> pageEvents = eventService.findEvents(page -1, size);
        List<Event> findEvents = pageEvents.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(eventMapper.eventsToEventResponses(findEvents),
                        pageEvents), HttpStatus.OK);
    }

}
