# LxStart_time
这个框架主要是用来管理第三方，和其他的数据初始化进行管理，从而对启动进行一定的优化
使用方法 ：在Application中添加 一个接口约束 和一个实现类
        AppInitTask.init(this,Sdk.class,InitAPPManger.class);
        在接口约束类中进行初始化的方法添加     @ModelLoadin(grade = 99, thredid = ThreadId.MAIN_THREAD_ID,Jumpout=false)
        
      grade 数值越大 优先级越高
      thredid 运行线程
       ThreadId.MAIN_THREAD_ID 主线成启动
        ThreadId.THREAD_ID 子线程启动
        Jumpout 在某个方法执行以后 调用运行其他方法
        如下
        public interface Sdk<T> {
           @ModelLoadin(grade = AppInitC.StartGrade.MAX, thredid = ThreadId.MAIN_THREAD_ID)
            T InitAppData();
          @ModelLoadin(grade = AppInitC.StartGrade.LIULISHUO, thredid =ThreadId.THREAD_ID)
          T LiuLishuosdk();
         @ModelLoadin(grade = AppInitC.StartGrade.FACE_BOOK, thredid = ThreadId.THREAD_ID)
          T Facebook();
       }
         public static Handler getHandler() {
        return mHandler;
    }

   //实现类
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
        
        或者 直接使用实现类
         AppInitTask.init(this,InitAPPManger.class);
         在实现类中，进行初始化的方法添加     @ModelLoadin(grade = 99, thredid = ThreadId.MAIN_THREAD_ID,Jumpout=false)
       //实现类
    public class InitAPPManger  {


    public static Handler getHandler() {
        return mHandler;
    }

    private static Handler mHandler;//主线程Handler
    Application context;

    @ModelLoadin(grade = 99, thredid = ThreadId.MAIN_THREAD_ID,Jumpout=false)
    public InitAPPManger InitAppData() {
        context = AppInitTask.getApplication();
        mHandler = new Handler();
        ContextUtils.getInstance().setContext(context);
        return this;
    }

    @ModelLoadin(grade = 98, thredid = ThreadId.MAIN_THREAD_ID,Jumpout=false)
    public InitAPPManger LiuLishuosdk() {

        FileDownloader.init(context);

        return this;
    }

    @ModelLoadin(grade = 97, thredid = ThreadId.MAIN_THREAD_ID,Jumpout=false)
    public InitAPPManger Facebook() {
        ImagePipelineConfig imagePipelineConfig = ImagePipelineConfig.newBuilder(context)
                .setDownsampleEnabled(true)
                .build();

        Fresco.initialize(context, imagePipelineConfig);
        return this;
    }

}

结束


  

         
