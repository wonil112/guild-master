package com.continewbie.guild_master.memeberevent.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MemberEventResponseDto {
    private long eventId; //한 멤버가 어떠한 이벤트에 참가한 신청서인지 알 수 있도록
    private long memberId;
    private String gameTier;
    private String selectedPosition;
}
