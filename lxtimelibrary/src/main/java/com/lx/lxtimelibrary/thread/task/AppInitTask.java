package com.lx.lxtimelibrary.thread.task;

import android.app.Application;

import androidx.annotation.IntDef;
import androidx.annotation.MainThread;

import com.lx.lxtimelibrary.thread.ThreadManager;
import com.lx.lxtimelibrary.thread.listner.AppJumpActivityListner;
import com.lx.lxtimelibrary.thread.type.ModelLoadin;
import com.lx.lxtimelibrary.utils.ProcessUtil;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * @Author : liuxin
 * @CreadData :8:02 PM
 * @Description :
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public class AppInitTask {

    public final static int MAIN_THREAD_ID = 0x001;
    public final static int THREAD_ID = 0x0101;

    @IntDef({MAIN_THREAD_ID, THREAD_ID})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ThreadId {
    }

    private static AppInitTask appInitTask;
    Class<?> clazz;
    private Map<Integer, Method> mainMethod = new TreeMap<Integer, Method>(
            new Comparator<Integer>() {
                @Override
                public int compare(Integer integer1, Integer integer2) {
                    return integer2.compareTo(integer1);
                }
            });
    private Map<Integer, Method> threadMethod = new TreeMap<Integer, Method>(
            new Comparator<Integer>() {
                @Override
                public int compare(Integer integer1, Integer integer2) {
                    return integer2.compareTo(integer1);
                }
            });
    private Object instance_class;

    public static Application getApplication() {
        return application;
    }

    private static Application application;


    /**
     * 不需要进程判断
     *
     * @param context
     * @param clazz   管理三方的实现方法
     */
    @MainThread
    public static AppInitTask init(Application context, Class<?> clazz) {
        application = context;
        if (appInitTask == null) {
            appInitTask = new AppInitTask(clazz);
        }
        return appInitTask;

    }


    /**
     * 需要进程判断
     *
     * @param context
     * @param clazz  管理三方类的接口或者父类
     * @param nmp      实现三方方法的类
     */
    @MainThread
    public static AppInitTask init(Application context, Class<?> clazz, Class<?> nmp) {
        application = context;
        if (appInitTask == null) {
            appInitTask = new AppInitTask(clazz, nmp);
        }
        return appInitTask;

    }


    public AppInitTask(Class<?> clazz) {
        try {
            instance_class = clazz.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        this.clazz = clazz;
        displayModel();

    }


    public AppInitTask(Class<?> clazz, Class<?> nmp) {
        this.clazz = clazz;
        try {
            instance_class = nmp.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        if (ProcessUtil.isMainProcess(application)) {
            displayModel();

        }


    }

    private void displayModel() {
        Method[] declaredMethods = clazz.getDeclaredMethods();//获得声明的成员变量

        for (Method method : declaredMethods) {
            if (method.getAnnotations() != null) {
                if (method.isAnnotationPresent(ModelLoadin.class)) {//如果属于这个注解
                    //为这个控件设置属性
                    method.setAccessible(true);//允许修改反射属性
                    ModelLoadin annotation = method.getAnnotation(ModelLoadin.class);
                    int thredid = annotation.thredid();
                    if (thredid == MAIN_THREAD_ID) {
                        mainMethod.put(annotation.grade(), method);
                    } else {
                        threadMethod.put(annotation.grade(), method);

                    }
                }
            }
        }
        printSortTask();
        dispatchAppThreadTask();
        dispatchAppMainTask();
    }

    /**
     * 执行主线成任务
     */
    private void dispatchAppMainTask() {
        Set<Integer> keySet = mainMethod.keySet();
        Iterator<Integer> iter = keySet.iterator();
        while (iter.hasNext()) {
            Integer key = iter.next();
            Method method = mainMethod.get(key);
            try {
                method.invoke(instance_class);
                ModelLoadin annotation = method.getAnnotation(ModelLoadin.class);
                if (annotation.Jumpout()) {
                    if (listner != null) {
                        listner.jump();
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 执行子线程任务
     */
    private void dispatchAppThreadTask() {
        ThreadManager.getInstance().getIOThreadPoolExecutor().execute(new Runnable() {
            @Override
            public void run() {
                Set<Integer> keySet = threadMethod.keySet();
                Iterator<Integer> iter = keySet.iterator();
                while (iter.hasNext()) {
                    Integer key = iter.next();
                    Method method = threadMethod.get(key);
                    try {
                        method.invoke(instance_class);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }




    public AppJumpActivityListner listner;

    /**
     * 暂时没考虑到怎么用 在某个缓解终止 直接跳转的
     *
     * @param listner
     */
    public void setJumListner(AppJumpActivityListner listner) {
        this.listner = listner;

    }

    //输出排好序的Task
    private void printSortTask() {
        StringBuilder sb = new StringBuilder();
        sb.append("当前所有任务排好的顺序为：");
        Iterator<Map.Entry<Integer, Method>> iterator = mainMethod.entrySet().iterator();
        sb.append("MainThread---");
        while (iterator.hasNext()) {
            Map.Entry<Integer, Method> entry = iterator.next();
            Integer key = entry.getKey();
            Method value = entry.getValue();
            sb.append(  key + ":" + value.getName()+"----->");
        }
        Iterator<Map.Entry<Integer, Method>> iterator2 = threadMethod.entrySet().iterator();
        sb.append( "Thread---");
        while (iterator2.hasNext()) {
            Map.Entry<Integer, Method> entry2 = iterator2.next();
            Integer key = entry2.getKey();
            Method value = entry2.getValue();
            sb.append( key + ":" + value.getName()+"----->");
        }
        System.out.println(sb.toString());
    }


}
