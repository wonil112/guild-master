package com.continewbie.guild_master.event.service;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.game.entity.Game;
import com.continewbie.guild_master.game.service.GameService;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.guild.repository.GuildRepository;
import com.continewbie.guild_master.guild.service.GuildService;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.member.service.MemberService;

import com.continewbie.guild_master.memberguild.entity.MemberGuild;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import com.continewbie.guild_master.event.repository.EventRepository;
import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;

import com.continewbie.guild_master.memeberevent.repository.MemberEventRepository;
import com.continewbie.guild_master.position.entity.Position;
import com.continewbie.guild_master.utils.validator.InvalidEventDateException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final MemberService memberService;
    private final GuildRepository guildRepository;
    private final MemberEventRepository memberEventRepository;
    private final GuildService guildService;
    private final GameService gameService;

    public EventService(EventRepository eventRepository, MemberService memberService, GuildRepository guildRepository, MemberEventRepository memberEventRepository, GuildService guildService, GameService gameService) {
        this.eventRepository = eventRepository;
        this.memberService = memberService;
        this.guildRepository = guildRepository;
        this.memberEventRepository = memberEventRepository;
        this.guildService = guildService;
        this.gameService = gameService;
    }

    // MemberGuild의 memberGuildRole이 MANAGER, MASTER이 아니라면 Event를 생성할 수 있음.
    public Event createEvent(Event event, Authentication authentication) {

        authenticateManager(event, authentication);
        verifiedDueDate(event);

        return eventRepository.save(event);
    }

    public Event updateEvent(Event event, Authentication authentication) {


        Event findEvent = findVerifiedEvent(event.getEventId());
        Guild guild = guildService.findGuild(findEvent.getGuild().getGuildId());
        Game game = gameService.findGame(guild.getGame().getGameId());

        Optional.ofNullable(event.getEventName())
                .ifPresent(eventName -> findEvent.setEventName(event.getEventName()));
        Optional.ofNullable(event.getEventContent())
                .ifPresent(eventContent -> findEvent.setEventContent(event.getEventContent()));
        Optional.ofNullable(event.getEventTotalPopulation())
                .ifPresent(eventTotalPopulation -> findEvent.setEventTotalPopulation(event.getEventTotalPopulation()));

        findEvent.setModifiedAt(LocalDateTime.now());
        findEvent.setGuild(guild);
        findEvent.getGuild().setGame(game);


        //findEvent에 길드를 넣어줘서
        authenticateManager(findEvent, authentication);

        return eventRepository.save(findEvent);
    }

    //1. 특정 이벤트 조회
    public Event findEvent(long eventId, Authentication authentication){
        Event event = findVerifiedEvent(eventId);

        if(event.getDueDate().isBefore(LocalDateTime.now())){
            event.setEventStatus(Event.EventStatus.EVENT_STATUS_COMPLETE);
            eventRepository.save(event);
        }
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
                Sort.by("startDate").ascending()));
    }

    //3. 특정 이벤트를 참여하는 기능
    public MemberEvent createAttendee (long eventId, MemberEvent memberEvent,
                                       Authentication authentication){

        Event findEvent = findVerifiedEvent(eventId);

        authenticatePlayer(findEvent, authentication);

        //이벤트에 인원이 꽉 찼으면 참석이 안되는 검증
        verifiedPopulation(findEvent);

        //동일한 이벤트에 두번 참석이 불가능.
        verifiedAlreadyAttendee(findEvent, memberEvent);

        findEvent.setEventCurrentPopulation(findEvent.getEventCurrentPopulation() + 1);

        // 특정 이벤트에서 MemberEvent목록을 가져와서 목록 하나 하나를 findMemberEvents에 넣어줌.
        List<MemberEvent> findMemberEvents = findEvent.getMemberEvents();
        findMemberEvents.add(memberEvent);

        eventRepository.save(findEvent);
        //참여하는 사람이 길드원인지 확인하는 검증

        return memberEvent;
    }

    //4. 특정 멤버가 참여한 전체 이벤트 조회
    public Page<Event> findMemberEvents(int page, int size, long memberId, Authentication authentication){

        String email = (String) authentication.getPrincipal();
        Member findMember = memberService.findVerifiedEmail(email);

        // 권한 확인 안해도 모두가 할 수 있으니 검증 안해도 됨.

        return eventRepository.findByMemberId(memberId, PageRequest.of(page, size,
                Sort.by("startDate").ascending()));
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


        return memberEventRepository.findByEventId(eventId, PageRequest.of(page, size,
                Sort.by("memberEventId").ascending()));

    }


    //6. 이벤트 참가 신청 삭제
    public void deleteAttendee (long eventId, long memberId, Authentication authentication){
        Event findEvent = findVerifiedEvent(eventId);

        //삭제하려는 사람이 매니저인지 확인
        authenticateManager(findEvent, authentication);

        //findMember의 MemberEvent를 삭제해야 함.
        Member findMember = memberService.findMember(memberId);

        // MemberEvent를 찾기
        Optional<MemberEvent> findMemberEvents = findEvent.getMemberEvents().stream()
                .filter(memberEvent -> memberEvent.getMember().getMemberId() == memberId)
                .findFirst();

        if (findMemberEvents.isPresent()) {
            // MemberEvent를 Event에서 삭제
            findEvent.getMemberEvents().remove(findMemberEvents.get());

            // Event 저장
            eventRepository.save(findEvent);
        } else{
            throw new BusinessLogicException(ExceptionCode.EVENT_NOT_FOUND);
        }

    }

    // 7. 이벤트 참가 직업별 현황
    public Map<String, Integer> findPositionCounts(long eventId){
        Event event = findVerifiedEvent(eventId);
        Optional<Guild> guild = guildRepository.findById((event.getGuild().getGuildId()));

        Guild findGuild = guild.orElseThrow(() -> new BusinessLogicException(ExceptionCode.GUILD_NOT_FOUND));
        Game game = findGuild.getGame();
        List<MemberEvent> memberEvents = memberEventRepository.findByEventId(eventId);

        Map<String, Integer> positionCounts = new HashMap<>();
        //어떤 게임인지 가져오고 그 게임의 포지션을 저장한 리스트를 생성.
        List<Position> positions = game.getPositionList();

        // 포지션 카운트 리스트에 포지션들 저장.
        for(Position position : positions) {
            for(int i=0; i<positions.size(); i++){
                positionCounts.put(position.getPositionName(), 0);
            }
        }

        //MemberEvents를 돌면서 positionCountList의 key == selectedPosition 이면 value +1
        for(MemberEvent memberEvent : memberEvents) {
            String selectedPosition = memberEvent.getSelectedPosition();

            if(positionCounts.containsKey(selectedPosition)) {
                positionCounts.put(selectedPosition, positionCounts.get(selectedPosition) + 1);
            }
        }
        return positionCounts;
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
        authenticateRole(findMember, event);
    }
    // (유효성 검증) MemberGuildRole이 길드원인지 확인
    public void authenticatePlayer(Event event, Authentication authentication){
        String email = (String) authentication.getPrincipal();
        Member findMember = memberService.findVerifiedEmail(email);

        //멤버의 멤버길드의 길드ID가 이벤트의 길드Id가 포함되어있을 때, 거기서의 권한을 확인
        authenticateRole(findMember, event);
    }

    public void authenticatePlayerByGuildId(long guildId, Authentication authentication){
        String email = (String) authentication.getPrincipal();
        Member findMember = memberService.findVerifiedEmail(email);

        Guild findGuild = guildRepository.findById(guildId).orElseThrow(()->
                new BusinessLogicException(ExceptionCode.GUILD_NOT_FOUND));

        boolean isTrue = false;

        for(MemberGuild memberGuild : findMember.getMemberGuildList()){
            if(memberGuild.getGuild().getGuildId() == findGuild.getGuildId()){
                //MemberGuildRole이 권한이 없으면 오류 발생
                isTrue = true;
                break;
            }
        }
        if(!isTrue){
            throw new BusinessLogicException(ExceptionCode.EVENT_NOT_PERMISSION);
        }
    }


    //멤버의 멤버길드의 길드ID가 이벤트의 길드Id가 포함되어있을 때, 거기서의 권한을 확인
    public void authenticateRole(Member findMember, Event event){
        boolean isTrue = false;

        //member -> memberGuilds ->  -> guild-id == event -> guild -> guild-id

        for(MemberGuild memberGuild : findMember.getMemberGuildList()){
            // member-id는 1인 memberGuild를 반환
            if(memberGuild.getGuild().getGuildId() == event.getGuild().getGuildId()){
                //MemberGuildRole이 권한이 없으면 오류 발생
                if(!memberGuild.getMemberGuildRoles().contains(MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_PLAYER)){
                    throw new BusinessLogicException(ExceptionCode.EVENT_NOT_PERMISSION);
                } // 이 if문은 없어도 무방하긴 함.

                isTrue = true;
            }
        }
        if(!isTrue){
            throw new BusinessLogicException(ExceptionCode.EVENT_NOT_PERMISSION);
        }
    }

    // 한 이벤트에 이미 참여한 지 확인하는 검증
    public void verifiedAlreadyAttendee(Event event, MemberEvent memberEvent){
        for(MemberEvent me : event.getMemberEvents()){
            if(memberEvent.getMember().getMemberId() == me.getMember().getMemberId()){
                throw new BusinessLogicException(ExceptionCode.EVENT_ALREADY_ATTEND);
            }
        }
    }

}
