package com.continewbie.guild_master.guild.dto;

import com.continewbie.guild_master.utils.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class GuildDto {

    @Getter
    @AllArgsConstructor
    public static class Post{
        private long gameId;
        @NotSpace
        @Pattern(regexp = "^[\\p{L}\\p{N}가-힣]{1,20}$", message = "길드명은 특수문자, 공백이 없는 1글자에서 20글자까지 가능합니다.")
        private String guildName;
        @NotSpace
        @Pattern(regexp = "^[\\p{L}\\p{N}가-힣]{1,20}$", message = "길드마스터명은 특수문자, 공백이 없는 1글자에서 15글자까지 가능합니다.")
        private String guildMasterName;
        @Range(min = 1, max = 100, message = "길드 최대 인원 수는 1~100명이어야 합니다.")
        private int guildTotalPopulation;
        @Size(min = 1, max = 100, message = "길드의 소개는 1~100글자이어야 합니다.")
        private String guildContent;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{
        private long guildId;
        @Range(min = 1, max = 100, message = "길드 최대 인원 수는 1~100명이어야 합니다.")
        private int guildTotalPopulation;
        @Size(min = 1, max = 100, message = "길드의 소개는 1~100글자이어야 합니다.")
        private String guildContent;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long guildId;
        private long gameId;
        private String guildName;
        private String guildMasterName;
        private int guildTotalPopulation;
        private int guildCurrentPopulation;
        private String guildContent;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private LocalDateTime deletedAt;
    }
}
