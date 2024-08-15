package com.continewbie.guild_master.member.service;

import com.continewbie.guild_master.exception.BusinessLogicException;
import com.continewbie.guild_master.exception.ExceptionCode;
import com.continewbie.guild_master.member.entity.Member;
import com.continewbie.guild_master.member.repository.MemberRepository;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private void verifyExistsEmail(String email){
        Optional<Member> member = memberRepository.findByEmail(email);
        if (member.isPresent()){
            throw new BusinessLogicException(ExceptionCode.MEMBER_EXISTS);
        }
    }


}
