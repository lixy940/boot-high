package com.lixy.boothigh.interceptor;

import com.lixy.boothigh.vo.LoginUserVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static final Log log = LogFactory.getLog(LoginInterceptor.class);


    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
        /**
         * 此处如果登录验证过不了，sw
         */
        //LoginUserVO vo = (LoginUserVO) request.getSession().getAttribute(BConstant.CURRENT_USER_KEY);
/*        LoginUserVO vo = new LoginUserVO();
        if (vo == null || StringUtils.isBlank(vo.getMobile())) {
            response.setHeader("Content-Type", "application/json;charset=utf-8");
            response.getWriter().write("用户未登录,请登录后重试");
            return false;
        }*/
        return true;
    }
}
