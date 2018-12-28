package com.wolf.app.data.trans;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务。这是默认值
 * </p>
 */

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = java.lang.Throwable.class, isolation = Isolation.READ_COMMITTED)
public @interface TransactionRequired {

}