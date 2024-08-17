package com.continewbie.guild_master.game.mapper;

import com.continewbie.guild_master.game.dto.GameDto;
import com.continewbie.guild_master.game.entity.Game;

import org.hibernate.annotations.Source;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring" , unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameMapper {


     GameDto.Response gameToGameResponseDto(Game game);

     List<GameDto.Response> gamesToGamesResponses(List<Game> games);
}
