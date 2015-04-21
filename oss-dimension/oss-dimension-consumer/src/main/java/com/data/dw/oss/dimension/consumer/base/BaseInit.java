package com.data.dw.oss.dimension.consumer.base;

import com.data.dw.oss.dimension.facade.LateArriveDimensionLookUpFacade;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by huangshiqian on 15/4/21.
 */
public class BaseInit {
    private ApplicationContext context;
    public LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade;
    BaseInit() {
        context = new ClassPathXmlApplicationContext("/testContext.xml");

        lateArriveDimensionLookUpFacade = (LateArriveDimensionLookUpFacade) context.getBean("lateArriveDimensionLookUpFacade");
    }
}
