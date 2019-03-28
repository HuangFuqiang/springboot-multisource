package com.code.springbootmultisource.common.aop;

import com.code.springbootmultisource.common.annotation.DataSource;
import com.code.springbootmultisource.common.multidatasource.DSEnum;
import com.code.springbootmultisource.common.multidatasource.DataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@ConditionalOnProperty(prefix = "code", name = "muti-datasource-open", havingValue = "true")
public class MultiSourceExAop implements Ordered {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultiSourceExAop.class);

    @Pointcut(value = "@annotation(com.code.springbootmultisource.common.annotation.DataSource)")
    private void cut() {}

    @Around("cut()")
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Signature signature = proceedingJoinPoint.getSignature();
        MethodSignature methodSignature = null;
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        methodSignature = (MethodSignature) signature;
        Object target = proceedingJoinPoint.getTarget();
        Method currentMethod = target.getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());

        DataSource dataSource = currentMethod.getAnnotation(DataSource.class);
        if (dataSource != null) {
            DataSourceContextHolder.setDataSourceType(dataSource.name());
            LOGGER.info("数据源设置为: " + dataSource.name());
        } else {
            DataSourceContextHolder.setDataSourceType(DSEnum.DATA_SOURCE_CORE);
            LOGGER.info("数据源设置为: " + DSEnum.DATA_SOURCE_CORE);
        }
        try {
            return proceedingJoinPoint.proceed();
        } finally {
            LOGGER.debug("清空数据源信息！");
            DataSourceContextHolder.clearDataSourceType();
        }
    }

    /**
     * aop的顺序要早于spring的事务
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
