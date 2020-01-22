package com.lixy.boothigh.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @Author: MR LIS
 * @Description:时间差计算工具,如方法执行时间，参考SystemLogAspect类的doControllerAround
 * @Date: Create in 14:14 2018/6/29
 * @Modified By:
 */
public class TimeBeginToEndExecuteCalUtils {

    private final static Logger logger = LoggerFactory.getLogger(TimeBeginToEndExecuteCalUtils.class);

    private final static ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>(){
        @Override
        protected Long initialValue() {
            return System.currentTimeMillis();
        }
    };

    public static final void begin(){
        TIME_THREADLOCAL.set(System.currentTimeMillis());

    }


    public static final long end(){
        return System.currentTimeMillis()-TIME_THREADLOCAL.get();
    }

    public static void main(String[] args)throws Exception {
        TimeBeginToEndExecuteCalUtils.begin();
        TimeUnit.SECONDS.sleep(1l);
        logger.info("Cost:{} mills", TimeBeginToEndExecuteCalUtils.end());
    }
}
