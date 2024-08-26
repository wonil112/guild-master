package com.continewbie.guild_master.memeberevent.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class MemberEventResponseDto {
    private long memberId;
    private long eventId;
    private String gameCode;
    private String gameTier;
    private String selectedPosition;
}
