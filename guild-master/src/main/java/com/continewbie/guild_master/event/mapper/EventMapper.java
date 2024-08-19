package com.continewbie.guild_master.event.mapper;

import com.continewbie.guild_master.event.dto.EventDto;
import com.continewbie.guild_master.event.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    Event eventPostDtoToEvent(EventDto.Post requestBody);
    Event eventPatchDtoToEvent(EventDto.Patch requestBody);
    EventDto.Response eventToEventResponseDto(Event event);
    List<EventDto.Response> eventsToEventResponses(List<Event> events);
}