package com.continewbie.guild_master.guild.service;

import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.guild.repository.GuildRepository;
import com.continewbie.guild_master.member.service.MemberService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GuildService {
    private final GuildRepository guildRepository;
    private final MemberService memberService;

    public GuildService(GuildRepository guildRepository, MemberService memberService) {
        this.guildRepository = guildRepository;
        this.memberService = memberService;
    }

    public Guild createGuild(Guild guild){
        return guildRepository.save(guild);
    }

    public Guild findGuild(long guildId){
        Guild findGuild = verifyFindIdGuild(guildId);
        return findGuild;
    }

    private Guild verifyFindIdGuild(long guildId){
        Optional<Guild> optionalGuild = guildRepository.findById(guildId);
        Guild findGuild  = optionalGuild.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        return findGuild;
    }
}
