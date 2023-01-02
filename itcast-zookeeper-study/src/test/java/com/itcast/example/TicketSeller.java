package com.itcast.example;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import static com.leetcode.util.LogUtil.info;
import static java.util.concurrent.TimeUnit.MICROSECONDS;

@Slf4j
public class TicketSeller {

    private static final int SLEEP_MILLIS = 5000;

    private void sell() {
        log.info("售票开始");
        try {
            MICROSECONDS.sleep(SLEEP_MILLIS);
        } catch(Exception e) {
            e.printStackTrace();
        }
        info("售票结束!");
    }

    public void sellTicketWithLock() throws Exception {
        MyLockTest lock = new MyLockTest();
        lock.acquireLock();
        sell();
        lock.releaseLock();
    }

    @Test
    public void test() throws Exception {
        TicketSeller seller = new TicketSeller();
        for (int i = 0; i < 10; i++) {
            seller.sellTicketWithLock();
        }
    }

}