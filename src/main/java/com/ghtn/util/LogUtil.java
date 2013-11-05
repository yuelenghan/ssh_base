package com.ghtn.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;

/**
 * 用aop在指定的方法中增加日志
 *
 * @author yuelenghan
 */
public class LogUtil {

    private static Logger logger = Logger.getLogger(LogUtil.class);

    public void afterReturn(JoinPoint joinPoint) {
        logger.debug("=======离开[" + joinPoint.getTarget().getClass() + "]类的["
                + joinPoint.getSignature().getName() + "]方法==========");
    }

    public void before(JoinPoint joinPoint) {
        logger.debug("=======进入[" + joinPoint.getTarget().getClass() + "]类的["
                + joinPoint.getSignature().getName() + "]方法==========");
    }

}
