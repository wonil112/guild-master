package com.continewbie.guild_master.event.entity;

import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.memeberevent.entity.MemberEvent;
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
public class Event extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long eventId;

    @Column(length = 20, nullable = false)
    private String eventName;

    @Column(length = 100)
    private String eventContent;

    @Column(nullable = false)
    private int eventTotalPopulation;

    @Column(nullable = false)
    private int eventCurrentPopulation = 1;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MemberEvent> memberEvents = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "guild_id")
    private Guild guild;

    public void setGuild(Guild guild){
        this.guild = guild;
        if(!guild.getEventList().contains(this)){
            guild.setEvent(this);
        }
    }
    

    public void addMemberEvent(MemberEvent memberEvent){
        memberEvents.add(memberEvent);
        if(memberEvent.getEvent()!=this){
            memberEvent.addEvent(this);
        }
    }
}
