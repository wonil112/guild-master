package com.continewbie.guild_master.game.dto;


import com.continewbie.guild_master.position.dto.PositionDto;
import com.continewbie.guild_master.position.entity.Position;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

public class GameDto {
    @Getter
    @AllArgsConstructor
    public static class Response{
        private long gameId;
        private List<PositionDto.Response> positionList;
        private String gameCode;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private LocalDateTime deletedAt;
    }
}
