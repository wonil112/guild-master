package com.continewbie.guild_master.guild.service;

import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.guild.repository.GuildRepository;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.memberguild.entity.MemberGuild;
import com.continewbie.guild_master.member.service.MemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static com.continewbie.guild_master.memberguild.entity.MemberGuild.MemberGuildStatus.MEMBER_GUILD_STATUS_ACTIVE;

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

        List<MemberGuild.MemberGuildStatus> statuses = Collections.singletonList(MEMBER_GUILD_STATUS_ACTIVE);
        memberGuild.setMemberGuildStatuses(statuses);
        guild.addMemberGuild(memberGuild);

        return guildRepository.save(guild);
    }
    
    @Transactional
    public MemberGuild registerGuild(long guildId, Authentication authentication, String nickName) {
        String email = (String) authentication.getPrincipal();
        Member findMember = memberService.findVerifiedEmail(email);
        Guild findGuild = verifyFindIdGuild(guildId);

        // 기능 추가 시작
        long gameId = findGuild.getGame().getGameId();

        // 1. 한 게임에 두 길드를 가입할 수 없음.
        List<MemberGuild> memberGuilds = findMember.getMemberGuildList();
        for(MemberGuild memberGuild : memberGuilds) {
            if(memberGuild.getGuild().getGame().getGameId() == gameId
            && memberGuild.getMemberGuildStatuses()
                    .contains(MEMBER_GUILD_STATUS_ACTIVE)){
                throw new BusinessLogicException(ExceptionCode.GUILD_LIMIT_ONE);
            }
        }

        //2. 길드의 마스터가

        //기능 추가 끝
        boolean hasExistingApplication = findMember.getMemberGuildList().stream()
                .anyMatch(mg -> mg.getGuild().getGuildId() == guildId &&
                        mg.getMemberGuildStatuses().contains(MemberGuild.MemberGuildStatus.MEMBER_GUILD_STATUS_WAIT));

        if (hasExistingApplication) {
            throw new BusinessLogicException(ExceptionCode.GUILD_ALREADY_ATTEND);
        }

        long pendingApplications = findMember.getMemberGuildList().stream()
                .filter(mg -> mg.getMemberGuildStatuses().contains(MemberGuild.MemberGuildStatus.MEMBER_GUILD_STATUS_WAIT))
                .count();

        if (pendingApplications >= 5) {
            throw new BusinessLogicException(ExceptionCode.GUILD_REGISTRATION_COUNT_MAX);
        }

        MemberGuild memberGuild = new MemberGuild();
        memberGuild.setNickName(nickName);
        memberGuild.setGuild(findGuild);
        memberGuild.setMember(findMember);
        List<MemberGuild.MemberGuildStatus> statuses = Collections.singletonList(MemberGuild.MemberGuildStatus.MEMBER_GUILD_STATUS_WAIT);
        memberGuild.setMemberGuildStatuses(statuses);
        findGuild.addMemberGuild(memberGuild);
        findGuild.setGuildCurrentPopulation(findGuild.getGuildCurrentPopulation() + 1);

        verifyGuildRegistration(memberGuild);
        return memberGuild;
    }



    @Transactional
    public MemberGuild acceptGuild(long guildId, long memberId, Authentication authentication) {
        verifyUserRoles(guildId,authentication);
        Guild findGuild = verifyFindIdGuild(guildId);

        MemberGuild findMemberGuild = findGuild.getMemberGuildList().stream()
                .filter(memberGuild -> memberGuild.getMember().getMemberId() == memberId)  // 멤버 ID로 필터링
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Member not found in the guild"));  // 예외 처리

        findMemberGuild.setNickName(findMemberGuild.getNickName());
        findMemberGuild.setMemberGuildRoles(List.of(MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_PLAYER));  // 역할을 PLAYER로 설정
        findMemberGuild.setMemberGuildStatuses(List.of(MemberGuild.MemberGuildStatus.MEMBER_GUILD_STATUS_ACTIVE));  // 상태를 ACTIVE로 설정
        findMemberGuild.getGuild().setGuildCurrentPopulation(findGuild.getGuildCurrentPopulation());
        guildRepository.save(findGuild);
        return findMemberGuild;
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

        return guildRepository.findAll(PageRequest.of(page, size, Sort.by("guildCurrentPopulation")));
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



    private void verifyGuildRegistration(MemberGuild memberGuild){
        if (memberGuild.getMemberGuildStatuses().contains(MEMBER_GUILD_STATUS_ACTIVE)){
            throw new BusinessLogicException(ExceptionCode.GUILD_ALREADY_EXISTS);
        }
        if (memberGuild.getMemberGuildStatuses().size() >= 5){
            throw new BusinessLogicException(ExceptionCode.GUILD_REGISTRATION_COUNT_MAX);
        }
    }

    private void verifyUserRoles(long guildId, Authentication authentication) {
        String email = (String) authentication.getPrincipal();
        Member member = memberService.findVerifiedEmail(email);

        boolean hasRole = member.getMemberGuildList().stream()
                .filter(memberGuild -> memberGuild.getGuild().getGuildId() == guildId)
                .anyMatch(memberGuild -> memberGuild.getMemberGuildRoles().contains(MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_MASTER) ||
                        memberGuild.getMemberGuildRoles().contains(MemberGuild.MemberGuildRole.MEMBER_GUILD_ROLE_MANAGER));

        if (!hasRole) {
            throw new AccessDeniedException("User does not have permission to accept members for this guild");
        }
    }


}

