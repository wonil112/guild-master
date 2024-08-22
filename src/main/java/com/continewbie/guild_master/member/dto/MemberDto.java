package com.continewbie.guild_master.member.dto;


import com.continewbie.guild_master.memberguild.dto.MemberGuildDto;
import com.continewbie.guild_master.memberguild.entity.MemberGuild;
import com.continewbie.guild_master.utils.validator.NotSpace;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDateTime;
import java.util.List;

public class MemberDto {
    @Getter
    @AllArgsConstructor
    public static class Post{
        @NotBlank
        @NotSpace
        @Pattern(regexp = "^[가-힣]{1,15}$", message = "이름은 공백 없이 한글로만 구성되며, 최대 15자까지 입력할 수 있습니다.")
        private String name;

        @NotBlank
        @Email
        private String email;

        @NotBlank@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).{8,20}$",
                message = "비밀번호는 8자 이상 20자 이하이며, 영어 대소문자, 숫자, 특수문자를 각각 하나 이상 포함해야 합니다.")
        private String password;

        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        @NotBlank
        private String phone;

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static class Patch{
        private long memberId;

        @NotSpace(message = "휴대폰 번호는 공백이 아니어야 합니다")
        @Pattern(regexp = "^[가-힣]{1,15}$", message = "이름은 공백 없이 한글로만 구성되며, 최대 15자까지 입력할 수 있습니다.")
        private String name;

        @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{};:,<.>]).{8,20}$",
                message = "비밀번호는 8자 이상 20자 이하이며, 영어 대소문자, 숫자, 특수문자를 각각 하나 이상 포함해야 합니다.")
        private String password;

        @NotSpace(message = "휴대폰 번호는 공백이 아니어야 합니다")
        @Pattern(regexp = "^010-\\d{3,4}-\\d{4}$",
                message = "휴대폰 번호는 010으로 시작하는 11자리 숫자와 '-'로 구성되어야 합니다.")
        private String phone;

        public void setMemberId(long memberId) {
            this.memberId = memberId;
        }
    }

    @Getter
    @AllArgsConstructor
    public static class Response{
        private long memberId;
        private List<MemberGuildDto.Response> memberGuilds;
        private String name;
        private String email;
        private String phone;
        private LocalDateTime createdAt;
        private LocalDateTime modifiedAt;
        private LocalDateTime deletedAt;
    }
}
