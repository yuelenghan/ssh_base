package com.ghtn.util;

import org.apache.log4j.Logger;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 用于观察方法的执行效率
 *
 * @author yuelenghan
 */
public class MethodEfficiencyUtil {
    private static Logger logger = Logger.getLogger(LogUtil.class);

    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object returnValue = joinPoint.proceed();

        long end = System.currentTimeMillis();

        logger.debug("=======执行[" + joinPoint.getTarget().getClass() + "]类的["
                + joinPoint.getSignature().getName() + "]方法用时"
                + (end - start) + "ms=======");

        return returnValue;

    }
}
