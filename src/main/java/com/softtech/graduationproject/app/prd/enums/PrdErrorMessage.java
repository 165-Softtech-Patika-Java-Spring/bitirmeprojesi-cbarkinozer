package com.softtech.graduationproject.app.prd.enums;

import com.softtech.graduationproject.app.gen.enums.BaseErrorMessage;

public enum PrdErrorMessage implements BaseErrorMessage {

    PRODUCT_NOT_FOUND("Product not found!"),
    FIELD_CANNOT_BE_NULL("Entered field cannot be null!"),
    FIELD_MUST_BE_POSITIVE("Entered field must be positive!")
    ;

    private String message;

    PrdErrorMessage(String message) {
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