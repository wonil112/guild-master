package com.continewbie.guild_master.member.mapper;


import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.member.dto.MemberDto;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.memberguild.dto.MemberGuildDto;
import com.continewbie.guild_master.memberguild.entity.MemberGuild;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberMapper {
    Member memberPostDtoToMember(MemberDto.Post requestBody);

    Member memberPatchDtoToMember(MemberDto.Patch requestBody);

    default MemberDto.Response memberToMemberResponse(Member member) {
        List<MemberGuildDto.Response> memberGuildDtos = member.getMemberGuildList().stream()
                .map(this::memberGuildToMemberGuildResponseDto)
                .collect(Collectors.toList());

        return new MemberDto.Response(
                member.getMemberId(),
                memberGuildDtos,
                member.getName(),
                member.getEmail(),
                member.getPhone(),
                member.getCreatedAt(),
                member.getModifiedAt(),
                member.getDeletedAt()
        );
    }

    List<MemberDto.Response> membersToMemberResponses(List<Member> members);



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