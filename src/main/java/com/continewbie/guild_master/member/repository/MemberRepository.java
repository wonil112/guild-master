package com.continewbie.guild_master.member.repository;

import com.continewbie.guild_master.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
<<<<<<< HEAD
    Optional<Member> findById(Long memberId);
    Optional<Member> findByMemberId(Long memberId);
=======
    Optional<Member> findByPhone(String phone);
>>>>>>> fba3364fc2a493f558f100abee0ecc52fa366b54
}
