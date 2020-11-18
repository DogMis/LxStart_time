package com.lx.myapplication;

import android.app.Application;

/**
 * @Author : liuxin
 * @CreadData :12:10 PM
 * @Description :
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
class ContextUtils {
    private static ContextUtils contextUtils;

    public Application getContext() {
        return context;
    }

    Application context;
    public static ContextUtils getInstance() {
        if (contextUtils==null){
            contextUtils = new ContextUtils();

        }
        return contextUtils;
    }

    public void setContext(Application context) {
        this.context=context;

    }
}
