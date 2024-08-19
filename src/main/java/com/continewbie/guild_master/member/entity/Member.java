package com.continewbie.guild_master.member.entity;

import com.continewbie.guild_master.auditable.Auditable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    List<MemberGuild> memberGuildList = new ArrayList<>();

    public void addMemberGuild(MemberGuild memberGuild){
        this.memberGuildList.add(memberGuild);
        if(memberGuild.getMember() != this){
            memberGuild.addMember(this);
        }
    }

}
