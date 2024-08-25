package com.continewbie.guild_master.guild.mapper;


import com.continewbie.guild_master.game.entity.Game;
import com.continewbie.guild_master.guild.dto.GuildDto;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.member.dto.MemberDto;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.memberguild.dto.MemberGuildDto;
import com.continewbie.guild_master.memberguild.entity.MemberGuild;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface GuildMapper {
    @Mapping(source = "gameId", target = "game.gameId")
    Guild guildPostDtoToGuild(GuildDto.Post requestBody);

    Guild guildPatchDtoToGuild(GuildDto.Patch requestBody);

//    @Mapping(source = "game.gameId", target = "gameId")
//    GuildDto.Response guildToGuildResponseDto(Guild guild);

    List<GuildDto.GuildResponse> guildsToGuildResponseDto(List<Guild> guilds);

    List<GuildDto.GuildMemberResponse> guildsToGuildMemberResponseDtos(List<Guild> guilds);

    @Mapping(source = "game.gameId",target = "gameId")
    GuildDto.GuildResponse guildToGuildResponseDto(Guild guild);

    default GuildDto.GuildMemberResponse guildToGuildMemberResponseDto(Guild guild) {
        List<MemberGuildDto.Response> memberGuildDtos = guild.getMemberGuildList().stream()
                .map(this::memberGuildToMemberGuildResponseDto)
                .collect(Collectors.toList());

        return new GuildDto.GuildMemberResponse(
                guild.getGuildId(),
                guild.getGame().getGameId(),
                guild.getGuildName(),
                guild.getGuildMasterName(),
                guild.getGuildTotalPopulation(),
                guild.getGuildCurrentPopulation(),
                guild.getGuildContent(),
                guild.getCreatedAt(),
                guild.getModifiedAt(),
                guild.getDeletedAt(),
                memberGuildDtos
        );
    }


    @Mapping(source = "member.memberId", target = "memberId") // Map Member's ID to Response DTO
    @Mapping(source = "guild.guildId", target = "guildId") // Map Guild's ID to Response DTO
    private MemberGuildDto.Response memberGuildToMemberGuildResponseDto(MemberGuild memberGuild) {

        MemberGuildDto.Response memberGuildResponseDto = new MemberGuildDto.Response(
                memberGuild.getMemberGuildId(),
                memberGuild.getMember().getMemberId(),
                memberGuild.getNickName(),
                memberGuild.getGuild().getGame().getGameId(),
                memberGuild.getGuild().getGuildId(),
                memberGuild.getGuild().getGuildName(),
                memberGuild.getGuild().getGuildTotalPopulation(),
                memberGuild.getGuild().getGuildCurrentPopulation(),
                memberGuild.getCreatedAt(),
                memberGuild.getDeletedAt(),
                memberGuild.getMemberGuildRoles(),
                memberGuild.getMemberGuildStatuses()
        );
        return memberGuildResponseDto;
    }
}