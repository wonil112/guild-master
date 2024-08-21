package com.continewbie.guild_master.exception;

import lombok.Getter;

    public enum ExceptionCode {
        MEMBER_NOT_FOUND(404, "Member not found"),
        MEMBER_EXISTS(409, "Member exists"),
        MEMBER_DIFFERENT(400, "Member Different"),
        GAME_NOT_FOUND(404, "Game not found"),
        INVALID_MEMBER_STATUS(400, "Invalid member status"),
        GUILD_NOT_FOUND(404,"guild not found"),
        GUILD_ALREADY_EXISTS(404,"Guild already exists"),
        EVENT_NOT_FOUND(404, "Event not found"),
        EVENT_MAX_PARTICIPANTS(400, "CurrentPopulation Over TotalPopulation");

        @Getter
        private int status;

        @Getter
        private String message;

        ExceptionCode(int code, String message) {
            this.status = code;
            this.message = message;
        }
}
