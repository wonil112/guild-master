package com.continewbie.guild_master.guild.service;

import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.guild.repository.GuildRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class GuildService {
    private final GuildRepository guildRepository;

    public GuildService(GuildRepository guildRepository) {
        this.guildRepository = guildRepository;
    }

    public Guild createGuild(Guild guild) {
        Optional<Guild> findGuild = guildRepository.findByGameIdAndGuildName(
                guild.getGame().getGameId(),
                guild.getGuildName());

        if (findGuild.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.GUILD_ALREADY_EXISTS);
        }

        return guildRepository.save(guild);
    }

    public Guild updateGuild(Guild guild) {
        Guild findGuild = verifyFindIdGuild(guild.getGuildId());
        Optional.ofNullable(guild.getGuildContent())
                .ifPresent(guildContent -> findGuild.setGuildContent(guild.getGuildContent()));
        Optional.ofNullable(guild.getGuildTotalPopulation())
                .ifPresent(guildTotalPopulation -> findGuild.setGuildTotalPopulation(guild.getGuildTotalPopulation()));
        return guildRepository.save(findGuild);
    }

    public void deleteGuild(long guildId) {
        Guild findGuild = verifyFindIdGuild(guildId);
        findGuild.setDeletedAt(LocalDateTime.now());
        guildRepository.save(findGuild);
    }


    public Guild findGuild(long guildId) {
        Guild findGuild = verifyFindIdGuild(guildId);
        return findGuild;
    }

    public Page<Guild> findGuilds(int page, int size) {

        return guildRepository.findAll(PageRequest.of(page, size, Sort.by("guildId")));
    }

    private Guild verifyFindIdGuild(long guildId) {
        Optional<Guild> optionalGuild = guildRepository.findById(guildId);
        Guild findGuild = optionalGuild.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.GUILD_NOT_FOUND));
        if (findGuild.getDeletedAt() != null) {
            throw new BusinessLogicException(ExceptionCode.GUILD_NOT_FOUND);
        }
        return findGuild;
    }

}
