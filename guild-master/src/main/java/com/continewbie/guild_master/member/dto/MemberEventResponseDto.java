package com.continewbie.guild_master.member.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class MemberEventResponseDto {
    private Long memberEventId; // MemberEvent의 ID

    private Long memberId; // Member의 ID

    private Long eventId; // Event의 ID

    private String gameTier; // 게임 티어

    private String selectedPosition; // 선택된 포지션

    private LocalDateTime createdAt; // 생성일

    private LocalDateTime modifiedAt; // 수정일

    private LocalDateTime deletedAt;
}
