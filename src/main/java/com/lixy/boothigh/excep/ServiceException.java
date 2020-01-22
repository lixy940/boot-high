package com.lixy.boothigh.excep;


import org.apache.commons.lang3.StringUtils;

import java.text.MessageFormat;

public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 7946023196149777499L;

    protected ServiceErrorCode errorCode;

    protected int realCode;

    private Object[] arguments;

    private Object dto;

    public ServiceErrorCode getErrorCode() {
        return errorCode;
    }

    public ServiceException(ServiceErrorCode errorCode, Object... arguments) {
        super();
        this.errorCode = errorCode;
        this.realCode = errorCode.getCode();
        this.arguments = arguments;
    }
    public ServiceException(ServiceErrorCode errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.realCode = errorCode.getCode();
    }

    public ServiceException(Object dto, Throwable cause) {
        super(cause);
        this.dto = dto;
    }

    public ServiceException(ServiceErrorCode errorCode, Object dto, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
        this.dto = dto;
    }

    /**
     * 取得配置文件的内容，或者使用默认值
     */
    @Override
    public String getMessage() {
        String notMessage = "not error, not message";
        if (errorCode == null) {
            return notMessage;
        } else {
            String defaultMessage = errorCode.getMsg();
            if (StringUtils.isBlank(defaultMessage)) {
                return notMessage;
            }
            if (this.arguments == null || this.arguments.length == 0) {
                return defaultMessage.replace("{0}", "");
            } else {
                return MessageFormat.format(defaultMessage, this.arguments);
            }

        }
    }

    public String getMessageTip() {
        String notMessage = "not error, not message";
        if (errorCode == null) {
            return notMessage;
        } else {
            String defaultMessage = errorCode.getMsg();

            if (this.arguments == null || this.arguments.length == 0) {
                notMessage = defaultMessage.replace("{0}", "");
            } else {
                notMessage =  MessageFormat.format(defaultMessage, this.arguments);
            }

           return notMessage!=null?notMessage.substring(notMessage.lastIndexOf(":")+1,notMessage.length()):notMessage;

        }
    }

    public Object getDto() {
        return dto;
    }

    public void setDto(Object dto) {
        this.dto = dto;
    }

    public Object[] getArguments() {
        return arguments;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }
    
    

}
