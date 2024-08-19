package com.continewbie.guild_master.position.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class PositionDto {
    @Getter
    @AllArgsConstructor
    public static class Response{
        private long positionId;
        private String positionName;
    }
}
