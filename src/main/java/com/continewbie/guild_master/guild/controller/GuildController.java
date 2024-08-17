package com.continewbie.guild_master.guild.controller;

import com.continewbie.guild_master.dto.SingleResponseDto;
import com.continewbie.guild_master.guild.dto.GuildDto;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.guild.mapper.GuildMapper;
import com.continewbie.guild_master.guild.service.GuildService;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;

@RestController
@RequestMapping("/guilds")
@Validated
@Slf4j
public class GuildController {

    private final static String DEFAULT_GUILD_URL = "/guilds";

    private final GuildMapper guildMapper;
    private final GuildService guildService;

    public GuildController(GuildMapper guildMapper, GuildService guildService) {
        this.guildMapper = guildMapper;
        this.guildService = guildService;
    }


    @PostMapping()
    public ResponseEntity postMember(@Valid @RequestBody GuildDto.Post requestBody) {
        Guild guild = guildMapper.GuildPostDtoToGuild(requestBody);
        Guild createdGuild = guildService.createGuild(guild);
        URI location = UriCreator.createUri(DEFAULT_GUILD_URL, createdGuild.getGuildId());
        return ResponseEntity.created(location).build();

    }

    @GetMapping("/{guild-id}")
    public ResponseEntity getGuild(@PathVariable("guild-id") @Positive long guildId){
        Guild guild = guildService.findGuild(guildId);
        return new ResponseEntity<>(new SingleResponseDto<>(guildMapper.guildToGuildResponseDto(guild)), HttpStatus.OK);
    }

}
