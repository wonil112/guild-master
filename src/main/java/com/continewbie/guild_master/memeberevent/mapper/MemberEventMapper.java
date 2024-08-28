package com.continewbie.guild_master.memeberevent.mapper;


import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.memberguild.entity.MemberGuild;
import com.continewbie.guild_master.memeberevent.dto.MemberEventDto;
import com.continewbie.guild_master.memeberevent.dto.MemberEventResponseDto;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MemberEventMapper {

    @Mapping(source = "requestBody.eventId", target ="event.eventId")
    @Mapping(source = "requestBody.member", target = "member")
    default MemberEvent memberEventDtoToMemberEvent(MemberEventDto requestBody){
        MemberEvent memberEvent = new MemberEvent();
        Event event = new Event();
        event.setEventId(requestBody.getEventId());
        memberEvent.setMember(requestBody.getMember());
        memberEvent.setEvent(requestBody.getEvent());
        memberEvent.setGameTier(requestBody.getGameTier());
        memberEvent.setSelectedPosition(requestBody.getSelectedPosition());

        return memberEvent;
    }

    default MemberEventResponseDto memberEventToMemberResponseDto(MemberEvent memberEvent) {
        MemberEventResponseDto memberEventResponseDto = new MemberEventResponseDto();
        List<MemberGuild> memberGuilds = memberEvent.getMember().getMemberGuildList();
        String nickname = "";

        for(MemberGuild memberGuild : memberGuilds){
            if(memberEvent.getEvent().getGuild().getGuildId() == memberGuild.getGuild().getGuildId()){
                nickname = memberGuild.getNickName();
                break;
            }
        }

        memberEventResponseDto.setMemberId(memberEvent.getMember().getMemberId());
        memberEventResponseDto.setNickname(nickname);
        memberEventResponseDto.setEventId(memberEvent.getEvent().getEventId());
        memberEventResponseDto.setGameCode(memberEvent.getEvent().getGuild().getGame().getGameCode());
        memberEventResponseDto.setGameTier(memberEvent.getGameTier());
        memberEventResponseDto.setSelectedPosition(memberEvent.getSelectedPosition());
        return memberEventResponseDto;
    }

    default List<MemberEventResponseDto> memberEventsToMemberEventResponseDtos(List<MemberEvent> memberEvents){
        return memberEvents.stream()
                .map(this::memberEventToMemberResponseDto)
                .collect(Collectors.toList());
    }
}