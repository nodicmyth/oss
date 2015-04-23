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

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@Description(
        name = "dim_lookUp",
        value = "_FUNC_(dim_name, key0, value0, key1, value1...) - create dimension key/value pairs by dimension model "
)
public class GenericLateArrivalDimensionLookUpUDF extends GenericUDF {
    HashMap<Object, Object> ret = new HashMap();
    private ObjectInspector[] ois;
    private LateArriveDimensionLookUpFacade lateArriveDimensionLookUpFacade;

    /**
     * 初始化HIVE程序,dubbo连接、参数拦截器等
     * @param arguments 参数的处理拦截器
     * @return 返回值处理拦截器
     * @throws UDFArgumentException
     */
    public ObjectInspector initialize(ObjectInspector[] arguments) throws UDFArgumentException {
        // 判断输入元素是否是符合 由模型名称 和 业务键的键值对组成的格式 参数个数大于3并且只会是奇数个
        if(arguments.length % 2 != 1 || arguments.length < 3) {
            throw new UDFArgumentLengthException("Arguments must be in [dimension_name, column_key/column_value pairs]");
        } else {

            // 保存各个传入参数的对象处理拦截器
            ois = arguments;

            // 参数中第一个元素是模型名称,因此只能是String类型
            if(!(arguments[0] instanceof StringObjectInspector)) {
                throw new UDFArgumentTypeException(1, "Dimension Name is expected String Type but " + arguments[0].getTypeName() + "\" is found");
            }

            // 参数中下标为奇数的是模型中业务键的名称,对应列名,需要String类型
            // 参数中下标为偶数的是模型中业务键的值,对应表中的数据,需要Primitive基础类型
            for(int keyOI = 1; keyOI < arguments.length; ++keyOI) {
                if(keyOI % 2 == 1) {
                    if(!(arguments[keyOI] instanceof StringObjectInspector)) {
                        throw new UDFArgumentTypeException(2, "Column Name is expected String Type but " + arguments[keyOI].getTypeName() + "\" is found");
                    }
                } else if(!(arguments[keyOI] instanceof PrimitiveObjectInspector)) {
                    throw new UDFArgumentTypeException(2, "Column Value is expected Primitive Type but " + arguments[keyOI].getTypeName() + "\" is found");
                }
            }

            // 规定HIVE的返回的key 和 value的格式, 代理键名称为String类型,代理键的值是Long类型
            ObjectInspector keyOI = PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.STRING);
            ObjectInspector valueOI = PrimitiveObjectInspectorFactory.getPrimitiveJavaObjectInspector(PrimitiveCategory.LONG);

            // 初始化DUBBO连接
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("testContext.xml");
            lateArriveDimensionLookUpFacade = (LateArriveDimensionLookUpFacade) context.getBean("lateArriveDimensionLookUpFacade");

            return ObjectInspectorFactory.getStandardMapObjectInspector(keyOI, valueOI);
        }
    }

    /**
     * 执行数据
     * @param arguments 处理的数据
     * @return 维度代理键MAP,key为代理键名称,value为代理键值
     * @throws HiveException
     */
    public Object evaluate(DeferredObject[] arguments) throws HiveException {
        this.ret.clear();

        // 初始化dubbo调用参数对象
        DimensionRequestDTO dto = new DimensionRequestDTO();
        dto.setDimensionName(PrimitiveObjectInspectorFactory.javaStringObjectInspector.getPrimitiveJavaObject(arguments[0].get()));
        Set<Column> columns = new HashSet<Column>();
        dto.setColumns(columns);

        // 初始化维度模型的业务键数据
        for(int i = 1; i < arguments.length; i += 2) {
            dto.addColumn(new Column(
                    PrimitiveObjectInspectorFactory.javaStringObjectInspector.getPrimitiveJavaObject(arguments[i].get())
                    , ((PrimitiveObjectInspector) ois[i + 1]).getPrimitiveJavaObject(arguments[i + 1].get())));
        }

        // 通过DUBBO服务查询并生成迟到的维度的代理键
        Column keyV = lateArriveDimensionLookUpFacade.lookUp(dto);

        // 将返回数据put进hive返回值
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

    @Override
    public void close() throws IOException {
        super.close();

        this.ret.clear();
        this.ret = null;
        this.ois = null;
        this.lateArriveDimensionLookUpFacade = null;
    }
}
