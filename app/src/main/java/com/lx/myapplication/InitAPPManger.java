package com.lx.myapplication;

import android.app.Application;
import android.os.Handler;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.liulishuo.filedownloader.FileDownloader;
import com.lx.lxtimelibrary.thread.task.AppInitTask;

/**
 * @Author : liuxin
 * @CreadData :10:20 AM
 * @Description : 第三方保管方法 可以随意
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class InitAPPManger implements Sdk<InitAPPManger> {


    public static Handler getHandler() {
        return mHandler;
    }

    private static Handler mHandler;//主线程Handler
    Application context;


    @Override
    public InitAPPManger InitAppData() {
        context = AppInitTask.getApplication();
        mHandler = new Handler();
        ContextUtils.getInstance().setContext(context);
        return this;
    }



    @Override
    public InitAPPManger LiuLishuosdk() {

        FileDownloader.init(context);

        return this;
    }

    @Override
    public InitAPPManger Facebook() {
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(context, imagePipelineConfig);
        return this;
    }







}
