package com.continewbie.guild_master.memeberevent.mapper;


import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.memeberevent.dto.MemberEventDto;
import com.continewbie.guild_master.memeberevent.dto.MemberEventResponseDto;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MemberEventMapper {
    default MemberEvent MemberEventDtoToMemberEvent(MemberEventDto requestBody){
        MemberEvent memberEvent = new MemberEvent();
        Event event = new Event();
        event = requestBody.getEvent();
//        event.setMemberEvents();

        memberEvent.setMember(requestBody.getMember());
        memberEvent.setEvent(requestBody.getEvent());
        memberEvent.setGameTier(requestBody.getGameTier());
        memberEvent.setSelectedPosition(requestBody.getSelectedPosition());

        return memberEvent;
    }

    List<MemberEventResponseDto> MemberEventsToMemberEventResponseDtos(List<MemberEvent> memberEvents);
}