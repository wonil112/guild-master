package com.continewbie.guild_master.event.dto;

import com.continewbie.guild_master.event.entity.Event;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;


public class  EventDto {

    @Getter
    @AllArgsConstructor
    public static class Post {

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
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Response {
        private long eventId;
//        private long guildId;
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