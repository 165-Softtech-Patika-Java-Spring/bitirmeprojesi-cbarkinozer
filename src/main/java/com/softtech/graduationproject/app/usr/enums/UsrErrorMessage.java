package com.softtech.graduationproject.app.usr.enums;

import com.softtech.graduationproject.app.gen.enums.BaseErrorMessage;

public enum UsrErrorMessage implements BaseErrorMessage {

    USER_NOT_FOUND("User not found!")
    ;

    private String message;

    UsrErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return message;
    }
}
