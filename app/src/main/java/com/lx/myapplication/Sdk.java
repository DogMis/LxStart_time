package com.lx.myapplication;

import com.lx.lxtimelibrary.thread.ThreadId;
import com.lx.lxtimelibrary.thread.type.ModelLoadin;

/**
 * @Author : liuxin
 * @CreadData :3:24 PM
 * @Description :
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
public interface Sdk<T> {
    @ModelLoadin(grade = AppInitC.StartGrade.MAX, thredid = ThreadId.MAIN_THREAD_ID)
    T InitAppData();



    @ModelLoadin(grade = AppInitC.StartGrade.LIULISHUO, thredid =ThreadId.THREAD_ID)
    T LiuLishuosdk();


    @ModelLoadin(grade = AppInitC.StartGrade.FACE_BOOK, thredid = ThreadId.THREAD_ID)
    T Facebook();




}
