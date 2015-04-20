/**
 * 
 */
package com.data.dw.oss.meta.test;

import com.data.dw.oss.meta.dto.Column;
import com.data.dw.oss.meta.dto.DimensionModel;
import com.data.dw.oss.meta.facade.DimensionModelFacade;
import com.data.dw.oss.meta.test.base.BaseBizTest;
import org.junit.Test;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;

/**
 * @author shiqian.huang
 * 
 */
public class OSSFacadeTest extends BaseBizTest {
    @Resource
    private ShardedJedis jedis;

    @Resource
    private DimensionModelFacade dimensionModelFacade;

    public ShardedJedis getJedis() {
        return jedis;
    }

    public void setJedis(ShardedJedis jedis) {
        this.jedis = jedis;
    }

    public DimensionModelFacade getDimensionModelFacade() {
        return dimensionModelFacade;
    }

    public void setDimensionModelFacade(DimensionModelFacade dimensionModelFacade) {
        this.dimensionModelFacade = dimensionModelFacade;
    }

    @Test
	public void testDimensionModelLookUp() {
		DimensionModel request = new DimensionModel();

        request.setDimensionName("DIM_PRODUCT");

		// System.out.println(securityManagerService.encryCouponNo("121212"));
		Column result = dimensionModelFacade.getDimKey(request);

		System.out.println(result);

//        System.out.println(jedis.hkeys("*"));

//        int len = 2;
//        Thread[] t = new Thread[len];
//        CountDownLatch count = new CountDownLatch(len);
//        for (int i = 0; i < len; i++) {
//            t[i] = new TestDimensionThread(count, lateArriveDimensionLookUpFacade);
//
//            t[i].start();
//        }
//
//        try
//        {
//            count.await();
//        }
//        catch (InterruptedException e)
//        {
//            e.printStackTrace();
//        }
//
//        System.out.println("main thread end...");
	}
}
