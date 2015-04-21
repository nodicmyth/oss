package com.data.dw.oss.dimension.consumer.base;

import com.data.dw.oss.dimension.facade.LateArriveDimensionLookUpFacade;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * Created by huangshiqian on 15/4/21.
 */
public class TestConsumer extends BaseInit {

    public void testLateArriveDimensionLookUp() {
        int len = 2;
        Thread[] t = new Thread[len];
        CountDownLatch count = new CountDownLatch(len);
        for (int i = 0; i < len; i++) {
            t[i] = new TestDimensionThread(count, lateArriveDimensionLookUpFacade);

            t[i].start();
        }

        try
        {
            count.await();
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }

        System.out.println("main thread end...");
    }

    public static void main(String[] args) {
        TestConsumer testConsumer = new TestConsumer();
        testConsumer.testLateArriveDimensionLookUp();
    }
}
