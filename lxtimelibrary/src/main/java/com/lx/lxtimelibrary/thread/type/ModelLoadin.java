package com.lx.lxtimelibrary.thread.type;

import com.lx.lxtimelibrary.thread.ThreadId;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @Author : liuxin
 * @CreadData :2:54 PM
 * @Description :
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
@Documented
@Target(METHOD)
@Retention(RUNTIME)
public @interface ModelLoadin {
    /**
     * @return
     * @grade 级别 越高优先运行级别越高
     */
    int grade() default -1;

    /**
     * thredid 线程
     * 只能使用指定type
     * @return
     */
    @ThreadId.ThreadType int thredid() default -1;

    /**
     * 直接 跳出当前初始化 直接跳转
     *
     * @return
     */
    boolean Jumpout() default false;

}
