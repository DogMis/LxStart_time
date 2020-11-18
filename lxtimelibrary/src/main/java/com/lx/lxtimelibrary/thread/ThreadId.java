package com.lx.lxtimelibrary.thread;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author : liuxin
 * @CreadData :4:10 PM
 * @Description :
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class ThreadId {
    public final static int MAIN_THREAD_ID=0x001;
    public final static int THREAD_ID=0x0101;
    @IntDef({MAIN_THREAD_ID, THREAD_ID})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ThreadType{}
}
