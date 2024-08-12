package com.continewbie.guild_master.member.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(nullable = false, updatable = false, unique = true)
    private String email;

    @Column(length = 20, nullable = false)
    private String password;

    @Column(length = 13, nullable = false)
    private String phone;

    @Enumerated(value = EnumType.STRING)
    private MemberStatus memberStatus = MemberStatus.MEMBER_STATUS_ACTIVE;

    @Column(nullable = false, updatable = false)
    LocalDateTime createdAt = LocalDateTime.now();

    @Column(nullable = false, name = "LAST_MODIFIED_AT")
    LocalDateTime modifiedAt = LocalDateTime.now();


    public enum MemberStatus{
        MEMBER_STATUS_ACTIVE("활동 중"),
        MEMBER_STATUS_SLEEP("휴면 상태"),
        MEMBER_STATUS_INACTIVE("탈퇴 상태");
        @Getter
        private String status;

        MemberStatus(String status){this.status = status;}
    }



}
