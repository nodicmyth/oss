package com.data.dw.oss.dimension.test;

import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;
import com.data.dw.oss.dimension.facade.LateArriveDimensionLookUpFacade;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

/**
 * Created by huangshiqian on 15/4/16.
 */
public class TestDimensionThread extends Thread {
    LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade;
    CountDownLatch count;

    public TestDimensionThread(CountDownLatch count, LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade) {
        this.lateArriveDimensionLookUpFacade = lateArriveDimensionLookUpFacade;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10000; i++) {
            DimensionRequestDTO request = new DimensionRequestDTO();
            Set<Column> columns = new HashSet<Column>();
            request.setDimensionName("dim_store");
            request.setColumns(columns);
            request.addColumn(new Column("first_name", "mike" + i));
            request.addColumn(new Column("last_name", "Hillyer"));
            // System.out.println(securityManagerService.encryCouponNo("121212"));
            Column result = lateArriveDimensionLookUpFacade.lookUp(request);

            System.out.println(result);
        }

        count.countDown();
    }
}
