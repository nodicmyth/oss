/**
 * 
 */
package com.data.dw.oss.dimension.consumer.test;

import com.data.dw.oss.dimension.consumer.test.base.BaseBizTest;
import com.data.dw.oss.dimension.facade.LateArriveDimensionLookUpFacade;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.concurrent.CountDownLatch;

/**
 * @author shiqian.huang
 * 
 */
public class OSSFacadeTest extends BaseBizTest {
    @Resource
    private LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade;

    public LateArriveDimensionLookUpFacade getLateArriveDimensionLookUpFacade() {
        return lateArriveDimensionLookUpFacade;
    }

    public void setLateArriveDimensionLookUpFacade(LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade) {
        this.lateArriveDimensionLookUpFacade = lateArriveDimensionLookUpFacade;
    }

    @Test
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

//		DimensionRequestDTO request = new DimensionRequestDTO();
//        Set<Column> columns = new HashSet<Column>();
//        request.setDimensionName("dim_store");
//        request.setDimKey(new Column("store_key"));
//		request.setColumns(columns);
//        request.addColumn(new Column("first_name", "mike9"));
//        request.addColumn(new Column("last_name", "Hillyer"));
//		// System.out.println(securityManagerService.encryCouponNo("121212"));
//        for (int i = 0; i < 10000; i++) {
//            Column result = lateArriveDimensionLookUpFacade.lookUp(request);
//
//            System.out.println(result);
//        }
	}
}
