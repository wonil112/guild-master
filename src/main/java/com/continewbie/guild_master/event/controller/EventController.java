package com.continewbie.guild_master.event.controller;

import com.continewbie.guild_master.dto.MultiResponseDto;
import com.continewbie.guild_master.event.dto.EventDto;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.member.service.MemberService;
import com.continewbie.guild_master.memeberevent.dto.MemberEventDto;
import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.memeberevent.dto.MemberEventResponseDto;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import com.continewbie.guild_master.event.mapper.EventMapper;
import com.continewbie.guild_master.memeberevent.mapper.MemberEventMapper;
import com.continewbie.guild_master.event.service.EventService;
import com.continewbie.guild_master.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
    private final MemberEventMapper memberEventMapper;
    private final MemberService memberService;


    public EventController(EventService eventService, EventMapper eventMapper, MemberEventMapper memberEventMapper, MemberService memberService) {
        this.eventService = eventService;
        this.eventMapper = eventMapper;
        this.memberEventMapper = memberEventMapper;
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity postEvent(@Valid @RequestBody EventDto.Post requestBody, Authentication authentication) {
        Event event = eventMapper.eventPostDtoToEvent(requestBody);
        Event createdEvent = eventService.createEvent(event, authentication);
        URI location = UriCreator.createUri(DEFAULT_EVENT_URL, createdEvent.getEventId());
        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{event-id}")
    public ResponseEntity patchEvent(@PathVariable("event-id") @Positive long eventId, @Valid @RequestBody EventDto.Patch requestBody,
                                     Authentication authentication) {
        requestBody.setEventId(eventId);
        Event event = eventMapper.eventPatchDtoToEvent(requestBody);
        Event updatedEvent = eventService.updateEvent(event, authentication);
        return new ResponseEntity<>(eventMapper.eventToEventResponseDto(updatedEvent), HttpStatus.OK);
    }

    //0. 특정 이벤트 삭제
    @DeleteMapping("/{event-id}")
    public ResponseEntity deleteEvent(@PathVariable("event-id") @Positive long eventId, Authentication authentication) {
        eventService.deleteEvent(eventId, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //1. 특정 이벤트 조회
    @GetMapping("/{event-id}")
    public ResponseEntity getEvent(@PathVariable("event-id") @Positive long eventId, Authentication authentication) {
        Event event = eventService.findEvent(eventId, authentication);
        return new ResponseEntity<>(eventMapper.eventToEventResponseDto(event), HttpStatus.OK);
    }

    //2. 특정 길드 내의 전체 이벤트 조회
    @GetMapping("/guilds/{guild-id}")
    public ResponseEntity getEventsForGuild(@PathVariable("guild-id") @Positive long guildId,
                                    @Positive @RequestParam int page,
                                    @Positive @RequestParam int size,
                                    Authentication authentication) {


        Page<Event> pageEvents = eventService.findGuildEvents(guildId, page - 1, size, authentication);

        List<Event> findEvents = pageEvents.getContent();
        List<EventDto.Response> testResponseDtos = eventMapper.eventsToEventResponses(findEvents);


        return new ResponseEntity<>(
                new MultiResponseDto<>(eventMapper.eventsToEventResponses((findEvents)),
                        pageEvents), HttpStatus.OK);
    }
    //3. 특정 이벤트를 참여하는 기능
    @PostMapping("/{event-id}/registration")
    public ResponseEntity postAttendee(@PathVariable("event-id") @Positive long eventId,
                                       @Valid @RequestBody MemberEventDto requestBody,
                                       Authentication authentication) {

        String email = (String)authentication.getPrincipal();
        Member findMember = memberService.findVerifiedEmail(email);
        requestBody.setMemberId(findMember.getMemberId());


        MemberEvent memberEvent = eventService.createAttendee(eventId,
                memberEventMapper.memberEventDtoToMemberEvent(requestBody), authentication);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    //4. 특정 멤버가 참여한 전체 이벤트 조회
    @GetMapping("/members")
    public ResponseEntity getEventsForMember(@Positive @RequestParam int page,
                                    @Positive @RequestParam int size,
                                    Authentication authentication) {

        Page<Event> pageEvents = eventService.findMemberEvents( page - 1, size, authentication);
        List<Event> findEvents = pageEvents.getContent();

        return new ResponseEntity<>(
                new MultiResponseDto<>(eventMapper.eventsToEventResponses(findEvents),
                        pageEvents), HttpStatus.OK);
    }

    //5. 특정 이벤트의 전체 참가자 조회
    @GetMapping("/{event-id}/registration")
    public ResponseEntity getAttendees(@PathVariable("event-id") @Positive long eventId,
                                       @Positive @RequestParam int page,
                                       @Positive @RequestParam int size,
                                       Authentication authentication) {
        Page<MemberEvent> pageMemberEvents = eventService.findAttendees(eventId, page - 1, size, authentication);

        List<MemberEvent> findMemberEvents = pageMemberEvents.getContent();
        List<MemberEventResponseDto> ResponseDtos =
                memberEventMapper.memberEventsToMemberEventResponseDtos(findMemberEvents);


        return new ResponseEntity<>(
                new MultiResponseDto<>(memberEventMapper.memberEventsToMemberEventResponseDtos(findMemberEvents),
                        pageMemberEvents), HttpStatus.OK);
    }


    //6. 이벤트 참가 신청 삭제
    @DeleteMapping("/{event-id}/{memberEvent-id}")
    public ResponseEntity deleteAttendees(@PathVariable("event-id") @Positive long eventId,
                                          @PathVariable("memberEvent-id") @Positive long memberEventId,
                                          Authentication authentication) {

        eventService.deleteAttendee(eventId, memberEventId, authentication);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
