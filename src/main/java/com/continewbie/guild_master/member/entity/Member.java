package com.continewbie.guild_master.member.entity;

import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
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

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 13, nullable = false)
    private String phone;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    List<MemberGuild> memberGuildList = new ArrayList<>();

    public void addMemberGuild(MemberGuild memberGuild){
        this.memberGuildList.add(memberGuild);
        if(memberGuild.getMember() != this){
            memberGuild.addMember(this);
        }
    }
    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<MemberEvent> memberEvents = new ArrayList<>();

    public void addMemberEvent(MemberEvent memberEvent) {
        memberEvents.add(memberEvent);
        if(memberEvent.getMember() != this) {
            memberEvent.addMember(this);
        }
    }

}
