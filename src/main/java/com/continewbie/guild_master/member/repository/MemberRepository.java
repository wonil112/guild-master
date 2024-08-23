package com.continewbie.guild_master.member.repository;

import com.continewbie.guild_master.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
<<<<<<< HEAD

    Optional<Member> findByPhone(String phone);

=======
    Optional<Member> findByPhone(String phone);
>>>>>>> 3b7decb0434037e2dad338b38f2f657e1779e198
}
