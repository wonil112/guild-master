package com.continewbie.guild_master.event.dto;

import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class MemberEventDto {

    @Positive
    private long eventId;

    private String gameTier;

    private String selectedPosition;
}
