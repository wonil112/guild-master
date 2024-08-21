package com.continewbie.guild_master.event.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberEventResponseDto {
    private long eventId;
    private String eventName;
    private String eventContent;
    private String gameTier;
    private String selectedPosition;

}
