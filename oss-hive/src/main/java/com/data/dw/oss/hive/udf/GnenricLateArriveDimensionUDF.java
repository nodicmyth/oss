package com.data.dw.oss.hive.udf;

import com.alibaba.fastjson.JSON;
import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;
import com.data.dw.oss.dimension.facade.LateArriveDimensionLookUpFacade;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.MapObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorConverters.Converter;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by huangshiqian on 15/4/20.
 */
@Description(name = "dim_lookUp"
, value = "_FUNC_(TABLE_NAME, COLUMN_MAP) - Returns Dimension Key Column Struct, Default NULL IF NOT FOUND")
public class GnenricLateArriveDimensionUDF extends GenericUDF {

    private LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade;

    private StringObjectInspector tableNameOI;
    private MapObjectInspector columnMapOI;

    private Converter[] converters;

    @Override
    public ObjectInspector initialize(ObjectInspector[] objectInspectors) throws UDFArgumentException {
        List structFieldNames = new LinkedList();
        List structFieldObjectInspectors = new LinkedList();

        // 判断输入参数是否长度合法
        if(objectInspectors.length != 2) {

            throw new UDFArgumentLengthException("Arguments Length must equals 2");
        } else {

            // 判断第一个参数是否是基本String类型
            if(!(objectInspectors[0] instanceof StringObjectInspector)) {

                throw new UDFArgumentTypeException(1, "Primitive Type is expected but " + objectInspectors[0].getTypeName() + "\" is found");
            }

            // 判断第二个参数是否是MAP类型
            if(!(objectInspectors[0] instanceof MapObjectInspector)) {

                throw new UDFArgumentTypeException(2, "Map Type is expected but " + objectInspectors[1].getTypeName() + "\" is found");
            }

            converters = new Converter[2];
            converters[0] = ObjectInspectorConverters.getConverter(PrimitiveObjectInspectorFactory.javaStringObjectInspector, PrimitiveObjectInspectorFactory.writableStringObjectInspector);
            converters[1] = ObjectInspectorConverters.getConverter(PrimitiveObjectInspectorFactory.javaLongObjectInspector, PrimitiveObjectInspectorFactory.writableLongObjectInspector);

            tableNameOI = (StringObjectInspector) objectInspectors[0];
            columnMapOI = (MapObjectInspector) objectInspectors[1];

            structFieldNames.add("column_name");
            structFieldNames.add("column_value");

            structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.writableStringObjectInspector );
            structFieldObjectInspectors.add(PrimitiveObjectInspectorFactory.writableLongObjectInspector );

            try {
                ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("testContext.xml");
                lateArriveDimensionLookUpFacade = (LateArriveDimensionLookUpFacade) context.getBean("lateArriveDimensionLookUpFacade");

                if(lateArriveDimensionLookUpFacade == null) {

                    throw new UDFArgumentException("Dubbo Facade IS NULL!");
                }
            } catch (Exception e) {

                throw e;
            }

        }

        return ObjectInspectorFactory.getStandardStructObjectInspector(structFieldNames, structFieldObjectInspectors);
    }

    @Override
    public Object evaluate(DeferredObject[] deferredObjects) throws HiveException {
//        LazyString LName = (LazyString)(deferredObjects[0].get());
//        LazyMap LMap = (Lazy)

        String tableName = tableNameOI.getPrimitiveJavaObject(deferredObjects[0].get());
        Map<String, Object> columns = (Map<String, Object>) columnMapOI.getMap(deferredObjects[1].get());

        DimensionRequestDTO dto = new DimensionRequestDTO();

        dto.setDimensionName(tableName);

        System.out.println("通过Map.entrySet遍历key和value");
        for (Map.Entry<String, Object> entry : columns.entrySet()) {
            dto.addColumn(new Column(entry.getKey(), entry.getValue()));
        }

        Column column = lateArriveDimensionLookUpFacade.lookUp(dto);
        Object[] e;
        e = new Object[2];
        e[0] = converters[0].convert(column.getName());
        e[1] = converters[1].convert(column.getValue());
        return e;
    }

    @Override
    public String getDisplayString(String[] children) {
        StringBuilder sb = new StringBuilder();
        sb.append("dim_lookUp(");

        assert children.length == 2;

        for(int i = 0; i < children.length; i += 2) {
            sb.append(children[i]);
            sb.append(":");
            sb.append(JSON.toJSONString(children[i + 1]));
            if(i + 2 != children.length) {
                sb.append(",");
            }
        }

        sb.append(")");
        return sb.toString();
    }

    @Override
    public void close() throws IOException {
        lateArriveDimensionLookUpFacade = null;
        super.close();
    }
}
