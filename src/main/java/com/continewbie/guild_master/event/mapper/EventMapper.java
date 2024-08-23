package com.continewbie.guild_master.event.mapper;

import com.continewbie.guild_master.event.dto.EventDto;
import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.memeberevent.dto.MemberEventResponseDto;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    //origin
    default Event eventPostDtoToEvent(EventDto.Post eventPostDto){
        Event event = new Event();
        Guild guild = eventPostDto.getGuild();
        event.setGuild(guild);
        event.setEventName(eventPostDto.getEventName());
        event.setEventContent(eventPostDto.getEventContent());
        event.setEventTotalPopulation(eventPostDto.getEventTotalPopulation());
        event.setDueDate(eventPostDto.getDueDate());
        event.setStartDate(eventPostDto.getStartDate());
        return event;
    }

    Event eventPatchDtoToEvent(EventDto.Patch eventPatchDto);

//    EventDto.Response eventToEventResponseDto(Event event);
//
//    List<EventDto.Response> eventsToEventResponses(List<Event> events);

    //patchDto
    default EventDto.Response eventToEventResponseDto(Event event){


        System.out.println("@".repeat(30));
        System.out.println(event.getEventName());
        System.out.println(event.getEventContent());
        System.out.println(event.getEventTotalPopulation());
        System.out.println(event.getDueDate());
        System.out.println(event.getStartDate());

        //원래 코드지만 빌더로 바꿈
        EventDto.Response eventResponseDto = new EventDto.Response();
        Guild guild = event.getGuild();
        eventResponseDto.setEventId(event.getEventId());
        eventResponseDto.setGuildId(guild.getGuildId());
        eventResponseDto.setGameId(guild.getGame().getGameId());
        eventResponseDto.setEventName(event.getEventName());
        eventResponseDto.setEventContent(event.getEventContent());
        eventResponseDto.setEventTotalPopulation(event.getEventTotalPopulation());
        eventResponseDto.setEventCurrentPopulation(event.getEventCurrentPopulation());
        eventResponseDto.setDueDate(event.getDueDate());
        eventResponseDto.setStartDate(event.getStartDate());
        eventResponseDto.setCreatedAt(event.getCreatedAt());
        eventResponseDto.setModifiedAt(event.getModifiedAt());

        return eventResponseDto;
    }


//    origin
    default List<EventDto.Response> eventsToEventResponses(List<Event> events) {

        return events.stream()
                .map(this::eventToEventResponseDto)
                .collect(Collectors.toList());
    }

}