package com.wolf.app.data.trans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 以非事务方式运行，如果当前存在事务，则把当前事务挂起
 * </p>
 */

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(rollbackFor = java.lang.Throwable.class, propagation = Propagation.NOT_SUPPORTED)
public @interface TransactionNotSupport {

}