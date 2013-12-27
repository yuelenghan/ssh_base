package com.ghtn.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * 用于观察方法的执行效率
 *
 * @author lh
 */
public class MethodEfficiencyUtil {
    private static Log log = LogFactory.getLog(LogUtil.class);

    /**
     * 包裹方法
     *
     * @param joinPoint 织入点
     * @return 方法执行的返回值
     * @throws Throwable 抛出所有异常
     */
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object returnValue = joinPoint.proceed();

        long end = System.currentTimeMillis();

        log.debug("执行[" + joinPoint.getTarget().getClass() + "]类的["
                + joinPoint.getSignature().getName() + "]方法用时"
                + (end - start) + "ms");

        // 如果一个方法执行时间超过10秒
        if (end - start > 10000) {
            log.warn("执行[" + joinPoint.getTarget().getClass() + "]类的["
                    + joinPoint.getSignature().getName() + "]方法耗时超过10秒!!!!!!!!!!!!!!!!");
        }

        return returnValue;

    }
}
