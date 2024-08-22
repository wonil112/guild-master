package com.continewbie.guild_master.memeberevent.dto;

import com.continewbie.guild_master.event.entity.Event;
import com.continewbie.guild_master.member.entity.Member;
import lombok.Getter;

import javax.validation.constraints.Positive;

@Getter
public class MemberEventDto {

    //PathVariable로 eventId를 받아서 MemberEvent가 갖고 있는 Event 객체에 넣어주기 위해 추가.
    @Positive
    private long eventId;

    @Positive
    private long memberId;

    private String gameTier;

    private String selectedPosition;

    public Member getMember(){
        Member member = new Member();
        member.setMemberId(memberId);
        return member;
    }

    public Event getEvent(){
        Event event = new Event();
        event.setEventId(eventId);
        return event;
    }
}