package com.lixy.boothigh.excep;

import com.lixy.boothigh.enums.ResultEnum;
import com.lixy.boothigh.vo.page.JsonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;


@ControllerAdvice
@ResponseBody
public class RestControlExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestControlExceptionHandler.class);

    /**
     * 业务异常
     * @param ex
     * @param request
     * @return
     */
    @ExceptionHandler(BussinessException.class)
    protected JsonResult bussinessException(Exception ex, HttpServletRequest request) {
        log.warn("rest接口调用异常, uri={}, msg={}", request.getRequestURI(), ex.getMessage(), ex);
        GenException genException = (GenException) ex;
        return new JsonResult(genException);
    }

    /**
     * 其他异常
     * @param exception
     * @param request
     * @return
     */
    @ExceptionHandler(ConstraintViolationException.class)
    JsonResult getRequestVaildException(ConstraintViolationException exception, HttpServletRequest request) {
        log.error("参数错误,uri={},e={}" ,request.getRequestURI(), exception);
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        StringBuilder msg = new StringBuilder();
        for (ConstraintViolation<?> item : violations) {
            msg.append(item.getMessage()).append(";");
        }
        JsonResult result = new JsonResult();
        result.setMessage(msg.toString());
        result.setState(ResultEnum.PARAM_ERROR.getValue());
        return result;
    }
}
