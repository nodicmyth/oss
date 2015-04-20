/**
 * 
 */
package com.data.dw.oss.meta.consumer.test;

import com.data.dw.oss.meta.consumer.test.base.BaseBizTest;
import com.data.dw.oss.meta.dto.Column;
import com.data.dw.oss.meta.dto.DimensionModel;
import com.data.dw.oss.meta.facade.DimensionModelFacade;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author shiqian.huang
 * 
 */
public class OSSFacadeTest extends BaseBizTest {
    @Resource
    private DimensionModelFacade dimensionModelFacade;


    @Test
	public void testLateArriveDimensionLookUp() {

        DimensionModel request = new DimensionModel();

        request.setDimensionName("DIM_PRODUCT");

        // System.out.println(securityManagerService.encryCouponNo("121212"));
        Column result = dimensionModelFacade.getDimKey(request);

        System.out.println(result);
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

    public DimensionModelFacade getDimensionModelFacade() {
        return dimensionModelFacade;
    }

    public void setDimensionModelFacade(DimensionModelFacade dimensionModelFacade) {
        this.dimensionModelFacade = dimensionModelFacade;
    }
}
