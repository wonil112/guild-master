package com.continewbie.guild_master.member.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberEventDto {

    @Positive
    private Long eventId;        // 관련된 Event의 ID

    @NotBlank(message = "게임 티어는 Not Blank")
    @Size(max = 15, message = "게임 티어는 15글자 이내여야 합니다.")
    private String gameTier;     // 게임 티어

    @NotBlank(message = "선택된 포지션은 Not Blank")
    @Size(max = 15, message = "포지션은 15글자 이내여야 합니다.")
    private String selectedPosition; // 선택된 포지션

}
