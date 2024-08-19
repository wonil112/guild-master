package com.continewbie.guild_master.event.dto;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.utils.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class  EventDto {
    @Getter
    @AllArgsConstructor
    public static class Post {

        @NotBlank
        private String eventName;
        @NotBlank
        private String eventContent;
        @NotNull
        private int eventTotalPopulation;
        @NotNull
        private LocalDateTime startDate;
        @NotNull
        private LocalDateTime dueDate;

    }

    @Getter
    @AllArgsConstructor
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

    @AllArgsConstructor
    @Getter
    public static class Response {
        private long eventId;
        private long guildId;
        private String eventName;
        private String eventContent;
        private int eventTotalPopulation;
        private int eventCurrentPopulation;
        private LocalDateTime startDate;
        private LocalDateTime dueDate;
        private Event.EventStatus eventStatus;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;

        public String getEventStatus() {
            return eventStatus.getStatus();
        }
    }
}