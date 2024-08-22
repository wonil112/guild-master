package com.continewbie.guild_master.member.service;

import com.continewbie.guild_master.auth.utils.JwtAuthorityUtils;
import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.helper.event.MemberRegistrationApplicationEvent;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.member.repository.MemberRepository;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final ApplicationEventPublisher publisher;
    private final PasswordEncoder passwordEncoder;
    private final JwtAuthorityUtils authorityUtils;

    public MemberService(MemberRepository memberRepository, ApplicationEventPublisher publisher, PasswordEncoder passwordEncoder, JwtAuthorityUtils authorityUtils) {
        this.memberRepository = memberRepository;
        this.publisher = publisher;
        this.passwordEncoder = passwordEncoder;
        this.authorityUtils = authorityUtils;
    }

    public Member createMember(Member member){;
        verifyExistsEmail(member.getEmail());
        verifyExistsPhone(member.getPhone());

        String encryptedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encryptedPassword);

        List<String> roles = authorityUtils.createRoles(member.getEmail());
        member.setRoles(roles);

        Member savedMember = memberRepository.save(member);

        publisher.publishEvent(new MemberRegistrationApplicationEvent(this, savedMember));
        return savedMember;
    }

    public Member findMember(long memberId){
        Member findMember = verifyFindMemberId(memberId);
        return findMember;
    }

    public Page<Member> findMembers(int page, int size){
        return memberRepository.findAll(PageRequest
                .of(page,size, Sort.by("memberId")));
    }

    public void deleteMember(long memberId, Authentication authentication){

        String email = (String)authentication.getPrincipal();
        Member findMember = findVerifiedEmail(email);

        if (findMember.getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_DIFFERENT);  // 예외 발생 처리
        }

        findMember.setDeletedAt(LocalDateTime.now());
        memberRepository.save(findMember);
    }

    public Member updateMember(Member member, Authentication authentication){

        String email = (String)authentication.getPrincipal();
        Member findMember = findVerifiedEmail(email);

        if (findMember.getMemberId() != member.getMemberId()) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_DIFFERENT);  // 예외 발생 처리
        }

        Optional.ofNullable(member.getName())
                .ifPresent( name -> findMember.setName(member.getName()));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.setPassword(member.getPassword()));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(member.getPhone()));

        return memberRepository.save(findMember);
    }


    public void verifyExistsEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()){
            throw new BusinessLogicException(ExceptionCode.ALREADY_EMAIL_EXISTS);
        }
    }
    public Member findVerifiedEmail(String email){
        Optional<Member> optionalMember= memberRepository.findByEmail(email);
        Member findMember  = optionalMember.orElseThrow(() ->
                new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }

    public void verifyExistsPhone(String phone){
        Optional<Member> member = memberRepository.findByPhone(phone);
        if (member.isPresent()){
            throw new BusinessLogicException(ExceptionCode.ALREADY_PHONE_EXISTS);
        }
    }


    public Member verifyFindMemberId(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember  = optionalMember.orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        if(findMember.getDeletedAt() != null ){
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND);
        }
        return findMember;
    }

}
