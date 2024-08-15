package com.continewbie.guild_master.member.dto;

import com.continewbie.guild_master.member.entity.Member;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class MemberDto {
    @Getter
    public static class Post{
        @NotBlank
        @Pattern(regexp = "^[가-힣]{1,15}$", message = "이름은 공백 없이 한글로만 구성되며, 최대 15자까지 입력할 수 있습니다.")
        private String name;

        @NotBlank
        @Email
        private String email;

        @NotBlank
        private String password;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        @NotBlank
        private String phone;
    }

    @Getter
    public static class Patch{
        private long memberId;

        @Pattern(regexp = "^[가-힣]{1,15}$", message = "이름은 공백 없이 한글로만 구성되며, 최대 15자까지 입력할 수 있습니다.")
        private String name;

        private String password;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        @NotBlank
        private String phone;

        private Member.MemberStatus memberStatus;

        public void setMemberId(long memberId) {this.memberId = memberId;}

    }

    @Getter
    public static class Response{
        private long memberId;
        private String name;
        private String email;
        private String phone;
        private Member.MemberStatus memberStatus;
        public String getMemberStatus(){return memberStatus.getStatus();}

    }
}
