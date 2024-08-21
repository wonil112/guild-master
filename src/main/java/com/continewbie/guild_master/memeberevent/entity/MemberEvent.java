package com.continewbie.guild_master.event.entity;

import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class MemberEvent extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberEventId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(nullable = false)
    private String gameTier;

    @Column(nullable = false)
    private String selectedPosition;

    public void addMember(Member member) {
        this.member = member;
        if (member != null && !member.getMemberEvents().contains(this)) {
            this.member.getMemberEvents().add(this);
        }
    }

    public void addEvent(Event event) {
        this.event = event;
        if (event != null && !event.getMemberEvents().contains(this)) {
            this.event.getMemberEvents().add(this);
        }
    }
}
