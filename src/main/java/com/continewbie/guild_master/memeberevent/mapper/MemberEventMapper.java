package com.continewbie.guild_master.event.mapper;


import com.continewbie.guild_master.event.dto.MemberEventDto;
import com.continewbie.guild_master.event.dto.MemberEventResponseDto;
import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.event.entity.MemberEvent;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MemberEventMapper {
    default MemberEvent MemberEventDtoToMemberEvent(MemberEventDto requestBody){
        MemberEvent memberEvent = new MemberEvent();
        memberEvent.setMember(requestBody.getMember());
        memberEvent.setEvent(requestBody.getEvent());
        memberEvent.setGameTier(requestBody.getGameTier());
        memberEvent.setSelectedPosition(requestBody.getSelectedPosition());

        return memberEvent;
    }

    List<MemberEventResponseDto> MemberEventsToMemberEventResponseDtos(List<MemberEvent> memberEvents);
}