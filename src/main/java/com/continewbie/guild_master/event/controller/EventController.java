package com.continewbie.guild_master.event.controller;

import com.continewbie.guild_master.event.dto.EventDto;
import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.event.mapper.EventMapper;
import com.continewbie.guild_master.event.repository.EventRepository;
import com.continewbie.guild_master.event.service.EventService;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.utils.UriCreator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/events")
@Validated
@Slf4j
public class EventController {
    private final static String DEFAULT_EVENT_URL = "/events";

    private final EventService eventService;
    private final EventMapper eventMapper;
    private final EventRepository eventRepository;

    public EventController(EventService eventService, EventMapper eventMapper, EventRepository eventRepository) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.eventRepository = eventRepository;
    }

    @PostMapping
    public ResponseEntity postEvent(@Validated @RequestBody EventDto.Post requestBody) {
        Event event = eventMapper.eventPostDtoToEvent(requestBody);
        Event createdEvent = eventService.createEvent(event);
        URI location = UriCreator.createUri(DEFAULT_EVENT_URL, createdEvent.getEventId());
        return ResponseEntity.created(location).build();
    }


}
