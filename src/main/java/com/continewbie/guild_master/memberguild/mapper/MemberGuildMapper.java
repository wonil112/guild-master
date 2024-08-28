package com.continewbie.guild_master.memberguild.mapper;

import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.memberguild.entity.MemberGuild;
import com.continewbie.guild_master.memberguild.dto.MemberGuildDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MemberGuildMapper {

    @Mapping(source = "requestBody.guildId", target = "guild.guildId") // Map the guildId to Guild entity
    @Mapping(source = "requestBody.member", target = "member") // Assuming requestBody.member is a Member entity
    default MemberGuild memberGuildPostDtoToMemberGuild(MemberGuildDto.Post requestBody){
        MemberGuild memberGuild = new MemberGuild();
        Guild guild = new Guild();
        guild.setGuildId(requestBody.getGuildId());

        memberGuild.setMember(requestBody.getMember());
        memberGuild.setGuild(requestBody.getGuild());
        memberGuild.setNickName(requestBody.getNickName());

        return memberGuild;
    }

    @Mapping(source = "member.memberId", target = "memberId") // Map Member's ID to Response DTO
    @Mapping(source = "guild.guildId", target = "guildId") // Map Guild's ID to Response DTO
    default MemberGuildDto.Response memberGuildToMemberGuildResponseDto(MemberGuild memberGuild) {

        MemberGuildDto.Response memberGuildResponseDto = new MemberGuildDto.Response(
                memberGuild.getMemberGuildId(),
                memberGuild.getMember().getMemberId(),
                memberGuild.getGuild().getGuildId(),
                memberGuild.getNickName(),
                memberGuild.getCreatedAt(),
                memberGuild.getDeletedAt(),
                memberGuild.getMemberGuildRoles(),
                memberGuild.getMemberGuildStatuses()
        );
        return memberGuildResponseDto;
    }

    List<MemberGuildDto.Response> memberGuildToMemberGuildResponseDtos(List<MemberGuild> memberGuilds);
}
