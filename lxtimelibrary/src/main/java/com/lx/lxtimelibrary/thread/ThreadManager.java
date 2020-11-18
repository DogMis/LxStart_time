package com.lx.lxtimelibrary.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author : liuxin
 * @CreadData :5:09 PM
 * @Description :
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class ThreadManager {
    private static volatile ThreadManager sTaskExceutorManager;
    // IO 密集型任务的线程池
    private ExecutorService mIOThreadPoolExecutor;

    public static ThreadManager getInstance() {
        if (sTaskExceutorManager ==null){
            synchronized (ThreadManager.class){
                if (sTaskExceutorManager ==null){
                    sTaskExceutorManager =new ThreadManager();
                }
            }
        }
        return sTaskExceutorManager;
    }
    //初始化线程池
    private ThreadManager(){

        mIOThreadPoolExecutor = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
    }
    public ExecutorService getIOThreadPoolExecutor() {
        return mIOThreadPoolExecutor;
    }
}
