package com.continewbie.guild_master.memeberevent.entity;

import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Setter
@Entity
public class MemberEvent extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberEventId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", referencedColumnName = "memberId")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", referencedColumnName = "eventId")
    private Event event;

    @Column(nullable = false)
    private String gameTier;

    @Column(nullable = false)
    private String selectedPosition;

    public void addMember(Member member) {
        this.member = member;
        if (member != null && !member.getMemberEvents().contains(this)) {
            this.member.addMemberEvent(this);
        }
    }

    public void addEvent(Event event) {
        this.event = event;
        if (event != null && !event.getMemberEvents().contains(this)) {
            this.event.addMemberEvent(this);
        }
    }
}
