package com.continewbie.guild_master.member.entity;

import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.guild.entity.Guild;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberGuild extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberGuildId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;

    private String nickName;

    public void addMember(Member member){
        this.member = member;
        if(!this.member.getMemberGuildList().contains(this)){
            this.member.addMemberGuild(this);
        }
    }


}
