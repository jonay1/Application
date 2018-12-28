package com.wolf.app.data.trans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 如果当前存在事务，则加入该事务；如果当前没有事务，则以非事务的方式继续运行
 * </p>
 */

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(value = "defaultTransactional", rollbackFor = java.lang.Throwable.class, propagation = Propagation.SUPPORTS)
public @interface TransactionSupports {

}