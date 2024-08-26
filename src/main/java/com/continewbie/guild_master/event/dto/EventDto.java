package com.continewbie.guild_master.event.dto;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.memeberevent.dto.MemberEventDto;
import com.continewbie.guild_master.memeberevent.dto.MemberEventResponseDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;


public class  EventDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

        @Positive
        private long guildId;



        @Pattern(regexp = "^[\\p{L}\\p{N}가-힣\\s]{1,20}$", message = "이벤트명은 특수문자를 제외하고, 공백을 포함한 1글자에서 20글자까지 가능합니다.")
        private String eventName;
        @Size(min = 1, max = 100, message = "이벤트 내용은 1~100글자이어야 합니다.")
        private String eventContent;
        @Range(min = 1, max = 100, message = "최대 인원 수는 1~100명이어야 합니다.")
        private int eventTotalPopulation;
        @NotNull(message = "시작일자는 필수")
        private LocalDateTime startDate;
        @NotNull(message = "시작일수는 필수")
        private LocalDateTime dueDate;

        // 이벤트를 생성만 해야하기 때문에, 이 리스트는 추후에 빼야 함.
        @Valid
        private List<MemberEventDto> memberEvents;

        public Guild getGuild(){
            Guild guild = new Guild();
            guild.setGuildId(guildId);
            return guild;
        }

    }

    @AllArgsConstructor
    @Getter
    public static class Patch {
        private long eventId;

        @Size(min = 1, max = 20, message = "이벤트 이름은 1~20글자이어야 합니다.")
        private String eventName;

        @Size(min = 1, max = 100, message = "이벤트 내용은 1~100글자이어야 합니다.")
        private String eventContent;

        @Range(min = 1, max = 100, message = "최대 인원 수는 1~100명이어야 합니다.")
        private int eventTotalPopulation;


        public void setEventId(long eventId) {this.eventId = eventId;}
    }

    @Setter
    @Getter
    public static class Response {
        private long eventId;
        private long gameId;
        private long guildId; // guildId 필요할까? 일단 알기 쉽게 적어두자.
        private String eventName;
        private String eventContent;
        private int eventTotalPopulation;
        private int eventCurrentPopulation;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime startDate;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime dueDate;
        private Event.EventStatus eventStatus;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

    }
}