package com.continewbie.guild_master.member.entity;

import com.continewbie.guild_master.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class Member extends Auditable {
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
    @Column(length = 20, nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_STATUS_ACTIVE;

    @OneToMany(mappedBy = "member")
    private List<MemberEvent> memberEvents = new ArrayList<>();

    public void addMemberEvent(MemberEvent memberEvent) {
        memberEvents.add(memberEvent);
        if(memberEvent.getMember() != this) {
            memberEvent.addMember(this);
        }
    }

    public enum MemberStatus {
        MEMBER_STATUS_ACTIVE("활동중"),
        MEMBER_STATUS_INACTIVE("비활동중"),
        MEMBER_STATUS_SLEEP("휴면중");

        @Getter
        private String status;

        MemberStatus(String status){
            this.status = status;
        }
    }


}
