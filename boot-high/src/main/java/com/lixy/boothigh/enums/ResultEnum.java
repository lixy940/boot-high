package com.lixy.boothigh.enums;

public enum ResultEnum {

    SUCCESS(200),
    NO_CONTENT(204),
    PARAM_ERROR(414),
    TOKEN_EXPIRE(415),
    SERVER_ERROR(500);

    ResultEnum(int value) {
        this.value = value;
    }

    private int value;

    public int getValue() {
        return value;
    }
}
