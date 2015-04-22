//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.data.dw.oss.hive.udf;

import com.data.dw.oss.dimension.dto.Column;
import com.data.dw.oss.dimension.dto.DimensionRequestDTO;
import com.data.dw.oss.dimension.facade.LateArriveDimensionLookUpFacade;
import org.apache.hadoop.hive.ql.exec.Description;
import org.apache.hadoop.hive.ql.exec.UDFArgumentException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentLengthException;
import org.apache.hadoop.hive.ql.exec.UDFArgumentTypeException;
import org.apache.hadoop.hive.ql.metadata.HiveException;
import org.apache.hadoop.hive.ql.udf.generic.GenericUDF;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.ObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector;
import org.apache.hadoop.hive.serde2.objectinspector.PrimitiveObjectInspector.PrimitiveCategory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.PrimitiveObjectInspectorFactory;
import org.apache.hadoop.hive.serde2.objectinspector.primitive.StringObjectInspector;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Description(
        name = "dim_lookUp",
        value = "_FUNC_(dim_name, key0, value0, key1, value1...) - Creates a map with the given [dimension_name, key/value pairs] "
)
public class GenericLateArrivalDimensionLookUpUDF extends GenericUDF {
    HashMap<Object, Object> ret = new HashMap();
    private ObjectInspector[] ois;
    private LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade;

    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        if(arguments.length % 2 != 1) {
            throw new UDFArgumentLengthException("Arguments must be in [dimension_name, column_key/column_value pairs]");
        } else {
            ois = arguments;
            if(!(arguments[0] instanceof StringObjectInspector)) {
                throw new UDFArgumentTypeException(1, "Dimension Name is expected String Type but " + arguments[0].getTypeName() + "\" is found");
            }

            for(int keyOI = 1; keyOI < arguments.length; ++keyOI) {
                if(keyOI % 2 == 1) {
                    if(!(arguments[keyOI] instanceof StringObjectInspector)) {
                        throw new UDFArgumentTypeException(2, "Column Name is expected String Type but " + arguments[keyOI].getTypeName() + "\" is found");
                    }
                } else if(!(arguments[keyOI] instanceof PrimitiveObjectInspector)) {
                    throw new UDFArgumentTypeException(2, "Column Value is expected Primitive Type but " + arguments[keyOI].getTypeName() + "\" is found");
                }
            }

            ObjectInspector keyOI = PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.STRING);
            ObjectInspector valueOI = PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.LONG);

            // 初始化DUBBO连接
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("testContext.xml");
            lateArriveDimensionLookUpFacade = (LateArriveDimensionLookUpFacade) context.getBean("lateArriveDimensionLookUpFacade");

            return ObjectInspectorFactory.getStandardMapObjectInspector(keyOI, valueOI);
        }
    }

    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        this.ret.clear();

        DimensionRequestDTO dto = new DimensionRequestDTO();
        dto.setDimensionName(PrimitiveObjectInspectorFactory.javaStringObjectInspector.getPrimitiveJavaObject(arguments[0].get()));
        Set<Column> columns = new HashSet<Column>();
        dto.setColumns(columns);

        for(int i = 1; i < arguments.length; i += 2) {
            dto.addColumn(new Column(
                    PrimitiveObjectInspectorFactory.javaStringObjectInspector.getPrimitiveJavaObject(arguments[i].get())
                    , ((PrimitiveObjectInspector) ois[i + 1]).getPrimitiveJavaObject(arguments[i + 1].get())));
        }


        Column keyV = lateArriveDimensionLookUpFacade.lookUp(dto);

        this.ret.put(keyV.getName(), keyV.getValue());

        return this.ret;
    }

    public String getDisplayString(String[] children) {
        StringBuilder sb = new StringBuilder();
        sb.append("dim_lookUp(");

        assert children.length % 2 == 1;

        for(int i = 0; i < children.length - 1; i += 2) {

            if(i == 0) {
                sb.append(children[0]);
            } else {
                sb.append(children[i]);
                sb.append(":");
                sb.append(children[i + 1]);

                if (i + 2 != children.length) {
                    sb.append(",");
                }
            }
        }

        sb.append(")");
        return sb.toString();
    }
}
