package com.lixy.boothigh.excep;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;

/**
 * @author MR LIS
 * @date 2019/4/25 17:56
 */
public class BussinessException extends RuntimeException implements GenException {

    private Integer code;

    private String description;

    public BussinessException(String message) {
        super(message);
        this.description = message;
    }

    public BussinessException(ExceptionType type, String message) {
        super(message);
        this.description = message;
        this.code = type.getCode();
    }

    public BussinessException(ExceptionType type) {
        this(type,type.getDescription());
    }

    public BussinessException(ExceptionType type, Object... args) {
        this(type, ArrayUtils.isNotEmpty(args) ? String.format(type.getDescription(), args) : type.getDescription());
    }

    public BussinessException(int exceptionCode, String message) {
        super(message);
        this.code = exceptionCode;
        this.description = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getDescription() {
        return description;
    }
}
