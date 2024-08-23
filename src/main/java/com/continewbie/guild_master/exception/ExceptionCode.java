package com.continewbie.guild_master.exception;

import lombok.Getter;

    public enum ExceptionCode {
        MEMBER_NOT_FOUND(404, "Member not found"),
        MEMBER_EXISTS(409, "Member exists"),
        MEMBER_DIFFERENT(400, "Member Different"),
        GAME_NOT_FOUND(404, "Game not found"),
        GUILD_REGISTRATION_DENIED(404, "Guild Registration Denied"),
        GUILD_NOT_FOUND(404,"guild not found"),
        GUILD_ALREADY_EXISTS(404,"Guild already exists"),
        EVENT_NOT_FOUND(404, "Event not found"),
        EVENT_MAX_PARTICIPANTS(400, "CurrentPopulation Over TotalPopulation"),
        EVENT_NOT_PERMISSION(404, "Event Not Permission"),
        EVENT_ALREADY_ATTEND(400, "Event Already Attend"),
        NO_PERMISSION(404,"No Permission"),
        GUILD_REGISTRATION_COUNT_MAX(404,"Guild registration is full"),
        ALREADY_PHONE_EXISTS(404,"Already Phone Exists"),
        ALREADY_EMAIL_EXISTS(404,"Already Email Exists");

        @Getter
        private int status;

        @Getter
        private String message;

        ExceptionCode(int code, String message) {
            this.status = code;
            this.message = message;
        }
}
