package com.continewbie.guild_master.guild.service;

import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.guild.repository.GuildRepository;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.member.entity.MemberGuild;
import com.continewbie.guild_master.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class GuildService {
    private final GuildRepository guildRepository;
    private final MemberService memberService;

    public GuildService(GuildRepository guildRepository, MemberService memberService) {
        this.guildRepository = guildRepository;
        this.memberService = memberService;
    }

    public Guild createGuild(Guild guild, Authentication authentication) {

        // Member 의 role 이 ROLE_USER 가 없다면 회원이 아니므로 길드 생성 불가.
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        // authentication 에서 Principal 에 있는 email 확인.
        String email = (String) authentication.getPrincipal();
        Member member = memberService.findVerifiedEmail(email);

        // 해당 member 의 memberGuild 를 조회해서 동일한 gameId 가 있으면 exception 발생.
        // 현재 email 이 memberGuild 안에 동일하게 있으면 exception 발생.
        for(MemberGuild memberGuild : member.getMemberGuildList()){
            if(guild.getGame().getGameId() == memberGuild.getGuild().getGame().getGameId()){
                throw new BusinessLogicException(ExceptionCode.GUILD_ALREADY_EXISTS);
            } else if(email.equals(memberGuild.getMember().getEmail())){
                throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
            }
        }

        // 생성하려는 길드의 이름이 동일한 게임 내에 존재하는지 확인하는 코드
        Optional<Guild> findGuild = guildRepository.findByGameIdAndGuildName(
                guild.getGame().getGameId(),
                guild.getGuildName());
        if (findGuild.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.GUILD_ALREADY_EXISTS);
        }

        // memberGuild 에 길드마스터 및 길드의 정보를 삽입.
        MemberGuild memberGuild = new MemberGuild();
        memberGuild.setMember(member);
        memberGuild.setGuild(guild);
        memberGuild.setNickName(guild.getGuildMasterName());

        List<MemberGuild.MemberGuildRole> roles = new ArrayList<>();
        roles.add(MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_PLAYER);
        roles.add(MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_MANAGER);
        roles.add(MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_MASTER);

        memberGuild.setMemberGuildRoles(roles);

        guild.addMemberGuild(memberGuild);

        return guildRepository.save(guild);
    }

    public Guild registrationGuild(Guild guild, Authentication authentication, String nickName) {

        // Member 의 role 이 ROLE_USER 가 없다면 회원이 아니므로 길드 생성 불가.
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        // authentication 에서 Principal 에 있는 email 확인.
        String email = (String) authentication.getPrincipal();
        Member member = memberService.findVerifiedEmail(email);

        // 해당 member 의 memberGuild 를 조회해서 동일한 gameId 가 있으면 exception 발생.
        // 현재 email 이 memberGuild 안에 동일하게 있으면 exception 발생.
        int registrationNumber = 0;
        for(MemberGuild memberGuild : member.getMemberGuildList()){
            if(guild.getGame().getGameId() == memberGuild.getGuild().getGame().getGameId()) {
                registrationNumber++;
            }
        }

        for(MemberGuild memberGuild : member.getMemberGuildList()){
            if(registrationNumber != 0 && memberGuild.getMemberGuildRoles().contains("MEMBER_GUILD_ROLE_PLAYER")){
                throw new BusinessLogicException(ExceptionCode.GUILD_ALREADY_EXISTS);
            } else if(registrationNumber >= 5) {
                throw new BusinessLogicException(ExceptionCode.GUILD_REGISTRATION_DENIED);
            } else if(email.equals(memberGuild.getMember().getEmail())){
                throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
            }
        }

        MemberGuild memberGuild = new MemberGuild();
        memberGuild.setMember(member);
        memberGuild.setGuild(guild);
        memberGuild.setNickName(nickName);

        guild.addMemberGuild(memberGuild);

        return guildRepository.save(guild);
    }

    public Guild acceptGuild(Guild guild, Authentication authentication, String nickName) {

        // Member 의 role 이 ROLE_USER 가 없다면 회원이 아니므로 길드 생성 불가.
        if(!authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }

        // authentication 에서 Principal 에 있는 email 확인.
        String email = (String) authentication.getPrincipal();
        Member member = memberService.findVerifiedEmail(email);

        // 해당 member 의 memberGuild 를 조회해서 동일한 gameId 가 있으면 exception 발생.
        // 현재 email 이 memberGuild 안에 동일하게 있으면 exception 발생.
        int registrationNumber = 0;
        for(MemberGuild memberGuild : member.getMemberGuildList()){
            if(guild.getGame().getGameId() == memberGuild.getGuild().getGame().getGameId()) {
                registrationNumber++;
            }
        }

        for(MemberGuild memberGuild : member.getMemberGuildList()){
            if(registrationNumber != 0 && memberGuild.getMemberGuildRoles().contains("MEMBER_GUILD_ROLE_PLAYER")){
                throw new BusinessLogicException(ExceptionCode.GUILD_ALREADY_EXISTS);
            } else if(registrationNumber >= 5) {
                throw new BusinessLogicException(ExceptionCode.GUILD_REGISTRATION_DENIED);
            } else if(email.equals(memberGuild.getMember().getEmail())){
                throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
            }
        }

        MemberGuild memberGuild = new MemberGuild();
        memberGuild.setMember(member);
        memberGuild.setGuild(guild);
        memberGuild.setNickName(nickName);

        guild.addMemberGuild(memberGuild);

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