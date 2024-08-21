package com.continewbie.guild_master.guild.mapper;


import com.continewbie.guild_master.game.entity.Game;
import com.continewbie.guild_master.guild.dto.GuildDto;
import com.continewbie.guild_master.guild.entity.Guild;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface GuildMapper {
    @Mapping(source = "gameId", target = "game.gameId")
    Guild guildPostDtoToGuild(GuildDto.Post requestBody);

    Guild guildPatchDtoToGuild(GuildDto.Patch requestBody);

    @Mapping(source = "game.gameId", target = "gameId")
    GuildDto.Response guildToGuildResponseDto(Guild guild);


    List<GuildDto.Response> guildsToGuildResponseDto(List<Guild> guilds);
}