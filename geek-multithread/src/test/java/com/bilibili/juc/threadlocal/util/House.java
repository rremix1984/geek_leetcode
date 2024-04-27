package com.bilibili.juc.threadlocal.util;

public class House {//资源类

    public int saleCount = 0;
    public synchronized void saleHouse() {
        ++saleCount;
    }
    public ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    /*ThreadLocal<Integer> saleVolume = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue()
        {
            return 0;
        }
    };*/

    public void saleVolumeByThreadLocal() {
        saleVolume.set(1 + saleVolume.get());
    }
}
