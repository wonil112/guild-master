package com.continewbie.guild_master.utils.validator;

import lombok.Getter;

@Getter
public class InvalidEventDateException extends RuntimeException{
    private final String message;
    private final int status;

    public InvalidEventDateException(int status, String message) {
        super(message);
        this.message = message;
        this.status = status;
    }
}
