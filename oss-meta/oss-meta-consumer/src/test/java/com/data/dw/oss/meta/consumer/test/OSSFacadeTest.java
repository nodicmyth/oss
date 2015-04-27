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

        Column result = dimensionModelFacade.getDimKey(request);

        System.out.println(result);
	}

    public DimensionModelFacade getDimensionModelFacade() {
        return dimensionModelFacade;
    }

    public void setDimensionModelFacade(DimensionModelFacade dimensionModelFacade) {
        this.dimensionModelFacade = dimensionModelFacade;
    }
}
