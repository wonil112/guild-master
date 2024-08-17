package com.continewbie.guild_master.guild.mapper;


import com.continewbie.guild_master.guild.dto.GuildDto;
import com.continewbie.guild_master.guild.entity.Guild;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface GuildMapper {
    Guild GuildPostDtoToGuild(GuildDto.Post requestBody);
    Guild guildToGuildResponseDto(Guild guild);
}
