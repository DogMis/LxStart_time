package com.lx.myapplication;

import android.app.Application;

import com.lx.lxtimelibrary.thread.task.AppInitTask;

/**
 * @Author : liuxin
 * @CreadData :12:06 PM
 * @Description :
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AppInitTask.init(this,Sdk.class,InitAPPManger.class);
    }
}
