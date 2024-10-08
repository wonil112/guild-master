package com.continewbie.guild_master.memberguild.dto;

import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.memberguild.entity.MemberGuild;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class MemberGuildDto {

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Post {
        private long memberId;
        private long guildId;

        @NotNull(message = "닉네임 입력은 필수입니다.")
        private String nickName;

        public Member getMember(){
            Member member = new Member();
            member.setMemberId(memberId);
            return member;
        }

        public Guild getGuild(){
            Guild guild = new Guild();
            guild.setGuildId(guildId);
            return guild;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Response {
        private long memberGuildId;
        private long memberId;
        private String nickName;
        private long gameId;
        private long guildId;
        private String guildName;
        private int guildTotalPopulation;
        private int guildCurrentPopulation;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm")
        private LocalDateTime deletedAt;
        private List<MemberGuild.MemberGuildRole> memberGuildRoles;
        private List<MemberGuild.MemberGuildStatus> memberGuildStatuses;
    }

}
