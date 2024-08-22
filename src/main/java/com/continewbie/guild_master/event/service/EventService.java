package com.continewbie.guild_master.event.service;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.guild.repository.GuildRepository;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.member.entity.MemberGuild;
import com.continewbie.guild_master.member.service.MemberService;

import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import com.continewbie.guild_master.event.repository.EventRepository;
import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;

import com.continewbie.guild_master.memeberevent.repository.MemberEventRepository;
import com.continewbie.guild_master.utils.validator.InvalidEventDateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final MemberService memberService;
    private final GuildRepository guildRepository;
    private final MemberEventRepository memberEventRepository;

    public EventService(EventRepository eventRepository, MemberService memberService, GuildRepository guildRepository, MemberEventRepository memberEventRepository) {
        this.eventRepository = eventRepository;
        this.memberService = memberService;
        this.guildRepository = guildRepository;
        this.memberEventRepository = memberEventRepository;
    }

    // MemberGuild의 memberGuildRole이 MANAGER, MASTER이 아니라면 Event를 생성할 수 있음.
    public Event createEvent(Event event, Authentication authentication) {

        authenticateManager(event, authentication);
        verifiedDueDate(event);

        return eventRepository.save(event);
    }

    public Event updateEvent(Event event, Authentication authentication) {

        authenticateManager(event, authentication);

        Event findEvent = findVerifiedEvent(event.getEventId());

        Optional.ofNullable(event.getEventName())
                .ifPresent(eventName -> findEvent.setEventName(eventName));
        Optional.ofNullable(event.getEventContent())
                .ifPresent(eventContent -> findEvent.setEventContent(eventContent));
        Optional.ofNullable(event.getEventTotalPopulation())
                .ifPresent(eventTotalPopulation -> findEvent.setEventTotalPopulation(eventTotalPopulation));

        return eventRepository.save(findEvent);
    }

    //1. 특정 이벤트 조회
    public Event findEvent(long eventId, Authentication authentication){
        Event event = findVerifiedEvent(eventId);
        authenticatePlayer(event, authentication);

        return event;
    }
    // 특정 이벤트 삭제
    public void deleteEvent(long eventId, Authentication authentication){
        Event findEvent = findVerifiedEvent(eventId);
        authenticateManager(findEvent, authentication);
        eventRepository.delete(findEvent);
    }

    //2. 특정 길드 내의 전체 이벤트 조회
    public Page<Event> findGuildEvents(long guildId, int page, int size, Authentication authentication){

        authenticatePlayerByGuildId(guildId, authentication);

        return eventRepository.findByGuildId(guildId, PageRequest.of(page, size,
                Sort.by("eventId").ascending()));
    }

    //3. 특정 이벤트를 참여하는 기능
    public MemberEvent createAttendee (long eventId, MemberEvent memberEvent,
                                       Authentication authentication){

        Event findEvent = findVerifiedEvent(eventId);
        authenticatePlayer(findEvent, authentication);

        verifiedPopulation(findEvent);


        findEvent.setEventCurrentPopulation(findEvent.getEventCurrentPopulation() + 1);

        // 특정 이벤트에서 MemberEvent목록을 가져와서 목록 하나 하나를 findMemberEvents에 넣어줌.
        List<MemberEvent> findMemberEvents = findEvent.getMemberEvents();
        findMemberEvents.add(memberEvent);

        eventRepository.save(findEvent);
        return memberEvent;
    }

    //4. 특정 멤버가 참여한 전체 이벤트 조회
    public Page<Event> findMemberEvents(int page, int size, Authentication authentication){

        String email = (String) authentication.getPrincipal();
        Member findMember = memberService.findVerifiedEmail(email);

        // 권한 확인 안해도 모두가 할 수 있으니 검증 안해도 됨.

        return eventRepository.findByMemberId(findMember.getMemberId(), PageRequest.of(page, size,
                Sort.by("eventId").ascending()));
    }


    public Event findVerifiedEvent(long eventId){
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        Event findEvent = optionalEvent.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.EVENT_NOT_FOUND));
        return findEvent;
    }

    //5. 특정 이벤트의 전체 참가자 조회
    public Page<MemberEvent> findAttendees(long eventId, int page, int size, Authentication authentication){
        Event findEvent = findVerifiedEvent(eventId);
        // 현재 요청한 유저가 해당 이벤트를 진행중인 길드의 길드원인지 확인
        authenticatePlayer(findEvent, authentication);

//        return eventRepository.findByEventId(findEvent.getEventId(), PageRequest.of(page, size,
//                Sort.by("memberId").ascending()));
        return memberEventRepository.findByEvent(findEvent, PageRequest.of(page, size,
                Sort.by("memberId").ascending()));

    }


    //6. 이벤트 참가 신청 삭제
    public void deleteAttendee (long eventId, long memberEventId, Authentication authentication){
        Event findEvent = findVerifiedEvent(eventId);
        authenticateManager(findEvent, authentication);

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

    // (유효성 검증) 현재 인원수 == 전체 인원수 인데 추가를 하려고 할 때 예외
    public void verifiedPopulation(Event event){

        if(event.getEventCurrentPopulation() == event.getEventTotalPopulation()){
            throw new BusinessLogicException(ExceptionCode.EVENT_MAX_PARTICIPANTS);
        }
    }

    // (유효성 검증) 종료 날짜가 시작 날짜보다 앞인지 확인
    public void verifiedDueDate(Event event){
        if (event.getStartDate().isAfter(event.getDueDate())) {
            throw new InvalidEventDateException(404, "종료 날짜는 시작 날짜보다 뒤여야 합니다");
        }
    }

    // (유효성 검증) MemberGuildRole이 매니저 또는 마스터인지 확인
    public void authenticateManager(Event event, Authentication authentication){
        String email = (String) authentication.getPrincipal();
        Member findMember = memberService.findVerifiedEmail(email);

        Guild findGuild = event.getGuild();

        //멤버의 멤버길드의 길드ID가 이벤트의 길드Id가 같을 때, 거기서의 권한을 확인
        authenticateRole(findMember, event, findMember.getMemberGuildList());
    }
    // (유효성 검증) MemberGuildRole이 길드원인지 확인
    public void authenticatePlayer(Event event, Authentication authentication){
        String email = (String) authentication.getPrincipal();
        Member findMember = memberService.findVerifiedEmail(email);

        Guild findGuild = event.getGuild();

        //멤버의 멤버길드의 길드ID가 이벤트의 길드Id가 같을 때, 거기서의 권한을 확인
        authenticateRole(findMember, event, findMember.getMemberGuildList());
    }

    public void authenticatePlayerByGuildId(long guildId, Authentication authentication){
        String email = (String) authentication.getPrincipal();
        Member findMember = memberService.findVerifiedEmail(email);

        Guild findGuild = guildRepository.findById(guildId).orElseThrow(()->
                new BusinessLogicException(ExceptionCode.GUILD_NOT_FOUND)
        );

        //멤버의 멤버길드의 길드ID가 이벤트의 길드Id가 같을 때, 거기서의 권한을 확인
        for(MemberGuild memberGuild : findMember.getMemberGuildList()){
            if(memberGuild.getGuild().getGuildId() == findGuild.getGuildId()){
                //MemberGuildRole이 권한이 없으면 오류 발생
                if(!memberGuild.getMemberGuildRoles().contains(MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_PLAYER)){
                    throw new BusinessLogicException(ExceptionCode.EVENT_NOT_PERMISSION);
                }
            }
            else {
                throw new BusinessLogicException(ExceptionCode.EVENT_NOT_PERMISSION);
            }
        }
    }

    //파라미터로 event를 받을 때 검증 메서드에서 중복 코드를 또 줄이기 위해 생성
    public void authenticateRole(Member findMember, Event event, List<MemberGuild> memberGuildList){
        for(MemberGuild memberGuild : findMember.getMemberGuildList()){
            if(memberGuild.getGuild().getGuildId() == event.getGuild().getGuildId()){
                //MemberGuildRole이 권한이 없으면 오류 발생
                if(!memberGuild.getMemberGuildRoles().contains(MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_PLAYER)){
                    throw new BusinessLogicException(ExceptionCode.EVENT_NOT_PERMISSION);
                }
            }
            else {
                throw new BusinessLogicException(ExceptionCode.EVENT_NOT_PERMISSION);
            }
        }
    }

}
