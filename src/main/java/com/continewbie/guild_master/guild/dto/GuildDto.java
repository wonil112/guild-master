package com.continewbie.guild_master.guild.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class GuildDto {

    @Getter
    @AllArgsConstructor
    public static class Post{
        private long gameId;
        private String guildName;
        private String guildMasterName;
        private int guildTotalPopulation;
        private String guildContent;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{

    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long guildId;
        private long gameId;
        private String guildName;
        private int guildTotalPopulation;
        private int guildCurrentPopulation;
        private String guildContent;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private LocalDateTime deletedAt;
    }
}
