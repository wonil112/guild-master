package com.continewbie.guild_master.member.service;

import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.member.repository.MemberRepository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Transactional
@Service
public class MemberService {
    private final MemberRepository memberRepository;


    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public Member createMember(Member member){;
        verifyExistsEmail(member.getEmail());
        return memberRepository.save(member);
    }

    public Member findMember(long memberId){
        Member findMember = verifyFindIdMember(memberId);
        return findMember;
    }

    public Page<Member> findMembers(int page, int size){
        return memberRepository.findAll(PageRequest
                .of(page,size, Sort.by("memberId")));
    }

    public void deleteMember(long memberId){
//      verifyFindIdMember() =  repository에서 memberId로 존재여부를 검증한 뒤 해당 Member를 반환하는 메서드
        Member findMember = verifyFindIdMember(memberId);
        findMember.setDeletedAt(LocalDateTime.now());
        memberRepository.save(findMember);
        
    }

    public Member updateMember(Member member){
        Member findMember = verifyFindIdMember(member.getMemberId());
        Optional.ofNullable(member.getName())
                .ifPresent( name -> findMember.setName(member.getName()));
        Optional.ofNullable(member.getPassword())
                .ifPresent(password -> findMember.setPassword(member.getPassword()));
        Optional.ofNullable(member.getPhone())
                .ifPresent(phone -> findMember.setPhone(member.getPhone()));

        return memberRepository.save(findMember);
    }


    private void verifyExistsEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }

    private Member verifyFindIdMember(long memberId){
        Optional<Member> optionalMember = memberRepository.findById(memberId);
        Member findMember  = optionalMember.orElseThrow(() ->
                    new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
        return findMember;
    }


}
