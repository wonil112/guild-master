package com.continewbie.guild_master.helper.event;

import com.continewbie.guild_master.member.entity.Member;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class MemberRegistrationApplicationEvent extends ApplicationEvent {
    private Member member;

    public MemberRegistrationApplicationEvent(Object source, Member member) {
        super(source);
        this.member = member;
    }
}
