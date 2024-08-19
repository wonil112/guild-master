package com.continewbie.guild_master.exception;

import lombok.Getter;

    public enum ExceptionCode {
        MEMBER_NOT_FOUND(404, "Member not found"),
        MEMBER_EXISTS(409, "Member exists"),
        GAME_NOT_FOUND(404, "Game not found"),
        INVALID_MEMBER_STATUS(400, "Invalid member status"),
<<<<<<< HEAD
        EVENT_NOT_FOUND(404, "Event not found"),
        EVENT_MAX_PARTICIPANTS(400, "CurrentPopulation Over TotalPopulation");
=======
        GUILD_NOT_FOUND(404,"guild not found"),
        GUILD_ALREADY_EXISTS(404,"Guild already exists");

>>>>>>> b463fd13f6d0237c67a05af613326ce7adf3d427

        @Getter
        private int status;

        @Getter
        private String message;

        ExceptionCode(int code, String message) {
            this.status = code;
            this.message = message;
        }
}
