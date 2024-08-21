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
import java.util.Collections;
import java.util.Optional;

import static com.continewbie.guild_master.member.entity.MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_MASTER;

@Service
public class GuildService {
    private final GuildRepository guildRepository;
    private final MemberService memberService;

    public GuildService(GuildRepository guildRepository, MemberService memberService) {
        this.guildRepository = guildRepository;
        this.memberService = memberService;
    }

    public Guild createGuild(Guild guild, Authentication authentication) {
        // user 권한이 없다면 member_need_login 코드 발생하면서 throw
        System.out.println("1. Authorities: " + authentication.getAuthorities());

//        if (!authentication.getAuthorities().contains(new SimpleGrantedAuthority("USER"))) {
//            throw new BusinessLogicException(ExceptionCode.MEMBER_NEED_LOGIN);
//        }
        // 이미 해당 게임에 속한 길드에 가입되어있다면 생성 불가.
        String email = (String)authentication.getPrincipal();
        Member member = memberService.findVerifiedEmail(email);
        for(MemberGuild memberGuild : member.getMemberGuildList()){
            if(guild.getGame().getGameId() == memberGuild.getGuild().getGame().getGameId()){
                throw new BusinessLogicException(ExceptionCode.GUILD_ALREADY_EXISTS);
            }
            else if(email.equals(memberGuild.getMember().getEmail())){
                throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
            }
        }
        System.out.println("2. Authorities: " + authentication.getAuthorities());
        System.out.println("3. Email: " + email);
        System.out.println("4. Member: " + member.getMemberId());

        Optional<Guild> findGuild = guildRepository.findByGameIdAndGuildName(
                guild.getGame().getGameId(),
                guild.getGuildName());

        if (findGuild.isPresent()) {
            throw new BusinessLogicException(ExceptionCode.GUILD_ALREADY_EXISTS);
        }

        Guild savedGuild = guildRepository.save(guild);

        MemberGuild memberGuild = new MemberGuild();
        System.out.println("5. MemberGuildId: " + memberGuild.getMemberGuildId());

        memberGuild.setMember(member);
        System.out.println("6. MemberGuild_MemberId: " + memberGuild.getMember().getMemberId());

        memberGuild.setGuild(guild);
        System.out.println("7. MemberGuild_GuildId: " + memberGuild.getGuild().getGuildId());

        memberGuild.setNickName(guild.getGuildMasterName());
        System.out.println("8. MemberGuild_NickName: " + memberGuild.getNickName());

        memberGuild.setMemberGuildRoles(Collections.singletonList(MEMBER_GUILD_ROLE_MASTER));
        System.out.println("9. MemberGuild_Roles: " + memberGuild.getMemberGuildRoles());

        member.getMemberGuildList().add(memberGuild);
        savedGuild.getMemberGuildList().add(memberGuild);

        return savedGuild;
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
