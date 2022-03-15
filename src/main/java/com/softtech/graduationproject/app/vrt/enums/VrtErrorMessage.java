package com.softtech.graduationproject.app.vrt.enums;

import com.softtech.graduationproject.app.gen.enums.BaseErrorMessage;

public enum VrtErrorMessage implements BaseErrorMessage {

    VAT_RATE_NOT_FOUND("VAT rate not found!"),
    ;

    private String message;

    VrtErrorMessage(String message) {
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
