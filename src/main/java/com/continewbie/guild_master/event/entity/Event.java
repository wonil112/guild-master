package com.continewbie.guild_master.event.entity;

import com.continewbie.guild_master.auditable.Auditable;
import com.continewbie.guild_master.guild.entity.Guild;
import com.continewbie.guild_master.memeberevent.dto.MemberEventResponseDto;
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
    private Integer eventTotalPopulation;

    @Column(nullable = false)
    private int eventCurrentPopulation = 0;

    @Column(nullable = false)
    private LocalDateTime startDate;

    @Column(nullable = false)
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus eventStatus = EventStatus.EVENT_STATUS_ACTIVE;

    public enum EventStatus {
        EVENT_STATUS_ACTIVE("활성"),
        EVENT_STATUS_COMPLETE("종료");

        @Getter
        private String status;

        EventStatus(String status){
            this.status = status;
        }
    }

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
