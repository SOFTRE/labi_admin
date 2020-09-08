package com.labi.core.intercept;

import com.labi.common.controller.BaseController;
import com.labi.core.util.HttpSessionHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.stereotype.Component;

/**
 * 静态调用session的拦截器
 *
 * @author lyr
 * @date 2016年11月13日 下午10:15:42
 */
@Aspect
@Component
public class SessionInterceptor extends BaseController {

    @Pointcut("execution(* com.labi.*..controller.*.*(..))")
    public void cutService() {

    }

    @Around("cutService()")
    public Object sessionKit(ProceedingJoinPoint point) throws Throwable {

        HttpSessionHolder.put(super.getHttpServletRequest().getSession());
        try {
            return point.proceed();
        } finally {
            HttpSessionHolder.remove();
        }
    }
}
