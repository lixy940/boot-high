package com.lixy.boothigh.controller;

import com.lixy.boothigh.excep.BussinessException;
import com.lixy.boothigh.excep.UserExceptionType;
import com.lixy.boothigh.vo.page.JsonResult;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author MR LIS
 * @date 2019/4/26 10:34
 */
@RequestMapping("/excep")
@Controller
public class ExceptionTestController {

    @ApiOperation(value = "异常测试", notes = "异常测试")
    @GetMapping("exceptionMethod")
    public JsonResult exceptionMethod() {
        throw new BussinessException(UserExceptionType.LOGIN_INFO_IS_ERROR, "测试异常");
//        return new JsonResult();
    }

}
