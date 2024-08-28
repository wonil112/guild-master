package com.continewbie.guild_master.guild.controller;

import com.continewbie.guild_master.dto.MultiResponseDto;
import com.continewbie.guild_master.dto.SingleResponseDto;
import com.continewbie.guild_master.guild.dto.GuildDto;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.guild.mapper.GuildMapper;
import com.continewbie.guild_master.guild.service.GuildService;
import com.continewbie.guild_master.memberguild.dto.MemberGuildDto;
import com.continewbie.guild_master.memberguild.entity.MemberGuild;
import com.continewbie.guild_master.memberguild.mapper.MemberGuildMapper;
import com.continewbie.guild_master.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/guilds")
@Validated
@Slf4j
public class GuildController {

    private final static String DEFAULT_GUILD_URL = "/guilds";

    private final GuildMapper guildMapper;
    private final GuildService guildService;
    private final MemberGuildMapper memberGuildMapper;

    public GuildController(GuildMapper guildMapper, GuildService guildService, MemberGuildMapper memberGuildMapper) {
        this.guildMapper = guildMapper;
        this.guildService = guildService;
        this.memberGuildMapper = memberGuildMapper;
    }

    @PostMapping
    public ResponseEntity postMember(@Valid @RequestBody GuildDto.Post requestBody, Authentication authentication) {
        Guild guild = guildMapper.guildPostDtoToGuild(requestBody);
        Guild createdGuild = guildService.createGuild(guild, authentication);
        URI location = UriCreator.createUri(DEFAULT_GUILD_URL, createdGuild.getGuildId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{guild-id}")
    public ResponseEntity patchGuild(@PathVariable("guild-id") @Positive long guildId, @RequestBody @Valid GuildDto.Patch requestBody) {
        Guild guild = guildMapper.guildPatchDtoToGuild(requestBody);
        guild.setGuildId(guildId);
        Guild updateGuild = guildService.updateGuild(guild);
        return new ResponseEntity<>(new SingleResponseDto<>(guildMapper.guildToGuildResponseDto(updateGuild)), HttpStatus.OK);
    }

    @DeleteMapping("/{guild-id}")
    public ResponseEntity deleteGuild(@PathVariable("guild-id") @Positive long guildId) {
        guildService.deleteGuild(guildId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{guild-id}")
    public ResponseEntity getGuild(@PathVariable("guild-id") @Positive long guildId) {
        Guild guild = guildService.findGuild(guildId);
        return new ResponseEntity<>(new SingleResponseDto<>(guildMapper.guildToGuildResponseDto(guild)), HttpStatus.OK);
    }

    @GetMapping("/{guild-id}/members")
    public ResponseEntity getGuildMember(@PathVariable("guild-id") @Positive long guildId) {
        Guild guild = guildService.findGuild(guildId);
        return new ResponseEntity<>(new SingleResponseDto<>(guildMapper.guildToGuildMemberResponseDto(guild)), HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity getGuilds(@Positive @RequestParam int page, @Positive @RequestParam int size) {
        Page<Guild> pageGuild = guildService.findGuilds(page - 1, size);
        List<Guild> guilds = pageGuild.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(guildMapper.guildsToGuildResponseDto(guilds), pageGuild), HttpStatus.OK);
    }

    @GetMapping("/members")
    public ResponseEntity getGuildMembers(@Positive @RequestParam int page, @Positive @RequestParam int size) {
        Page<Guild> pageGuild = guildService.findGuilds(page - 1, size);
        List<Guild> guilds = pageGuild.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(guildMapper.guildsToGuildMemberResponseDtos(guilds), pageGuild), HttpStatus.OK);
    }


    @PostMapping("/{guild-id}/registration")
    public ResponseEntity<String> registerMember(@PathVariable("guild-id") long guildId,
                                                 @RequestBody MemberGuildDto.Post requestBody, Authentication authentication) {
        guildService.registerGuild(guildId,authentication,requestBody.getNickName());
        MemberGuild memberGuild = memberGuildMapper.memberGuildPostDtoToMemberGuild(requestBody);
        URI location = UriCreator.createUri(DEFAULT_GUILD_URL+"/registration", memberGuild.getMemberGuildId());
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/{guild-id}/members/{member-id}/accept")
    public ResponseEntity<String> acceptMember(@PathVariable("guild-id") @Positive long guildId, @PathVariable("member-id") @Positive long memberId, Authentication authentication) {
        MemberGuild memberGuild = guildService.acceptGuild(guildId,memberId,authentication);

        return new ResponseEntity(new SingleResponseDto<>(memberGuildMapper.memberGuildToMemberGuildResponseDto(memberGuild)),HttpStatus.OK);
    }
}