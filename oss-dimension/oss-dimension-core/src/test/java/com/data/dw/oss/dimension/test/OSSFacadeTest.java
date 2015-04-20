/**
 * 
 */
package com.data.dw.oss.dimension.test;

import com.data.dw.oss.dimension.facade.LateArriveDimensionLookUpFacade;
import com.data.dw.oss.dimension.test.base.BaseBizTest;
import org.junit.Test;
import redis.clients.jedis.ShardedJedis;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author shiqian.huang
 * 
 */
public class OSSFacadeTest extends BaseBizTest {
    @Resource
    private ShardedJedis jedis;

    @Resource
    private LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade;

    public ShardedJedis getJedis() {
        return jedis;
    }

    public void setJedis(ShardedJedis jedis) {
        this.jedis = jedis;
    }

    public LateArriveDimensionLookUpFacade getLateArriveDimensionLookUpFacade() {
        return lateArriveDimensionLookUpFacade;
    }

    public void setLateArriveDimensionLookUpFacade(LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade) {
        this.lateArriveDimensionLookUpFacade = lateArriveDimensionLookUpFacade;
    }

    @Test
	public void testDimensionLookUp() {
//		DimensionRequestDTO request = new DimensionRequestDTO();
//        Set<Column> columns = new HashSet<Column>();
//        request.setDimensionName("dim_store");
//        request.setDimKey(new Column("store_key"));
//		request.setColumns(columns);
//        request.addColumn(new Column("first_name", "mike9"));
//        request.addColumn(new Column("last_name", "Hillyer"));
//		// System.out.println(securityManagerService.encryCouponNo("121212"));
//		Column result = lateArriveDimensionLookUpFacade.lookUp(request);
//
//		System.out.println(result);
//
//        System.out.println(jedis.hkeys("*"));

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
}
