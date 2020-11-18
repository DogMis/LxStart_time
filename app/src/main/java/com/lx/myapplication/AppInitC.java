package com.lx.myapplication;

/**
 * @Author : liuxin
 * @CreadData :1:55 PM
 * @Description :
 * @UpdateUser: 更新者：
 * @UpdateRemark: 更新说明：
 */
interface AppInitC {


    /**
     * 没次添加启动时等级 max1000
     */
    interface StartGrade{
        int MAX=1000;

        /**
         * 0-100 THREAD
         */
        int LIULISHUO = 4;
        int FACE_BOOK = 6;

    }
}
