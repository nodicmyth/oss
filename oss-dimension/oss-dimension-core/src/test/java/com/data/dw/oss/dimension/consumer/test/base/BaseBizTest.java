package com.data.dw.oss.dimension.consumer.test.base;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.jmock.Mockery;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.qunar.pay.g2.rsi.RemoteServiceFactory;
//import com.qunar.pay.g2.unittest.ScriptRunnerWrapper;

/**
 * biz层测试基类
 * 
 * @Copyright: Copyright (c)2011
 * @company qunar.com
 * @author xingwei.bi
 * @since 2011-6-8
 * @version 1.0
 */
@ContextConfiguration(locations = { "classpath:/testContext.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)
public class BaseBizTest {

	static {
		//禁用远程调用组件
		System.setProperty("disablersi", "true");
		System.setProperty("mockconfig", "true");
		System.setProperty("disablemsg", "true");
		System.setProperty("disablecache", "true");
		RemoteServiceFactory.init();
	}

//	private static ScriptRunnerWrapper scriptRunnerWrapper;
	
	protected Mockery mockery = new Mockery();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeClass
	public static void initTestData() throws IOException, SQLException {
		ApplicationContext context = new ClassPathXmlApplicationContext("/testContext.xml");
	}
	
	@AfterClass  
	public static void afterDaoTest() {
	}


	public void save(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void update(String sql) {
		jdbcTemplate.execute(sql);
	}

	public void delete(String sql) {
		jdbcTemplate.execute(sql);
	}

	/**
	 * List 里的每个对应是一个Map
	 * */
	@SuppressWarnings("rawtypes")
	public List query(String sql) {
		return jdbcTemplate.queryForList(sql);
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}
	
	/**
	 * 执行sql获取Map ,map属性匹配器，只判断属性是否相等(equalTo)<br>
	 * propertyPairs 格式为："propertyName1:propertyValue1,propertyName2:propertyValue2"<br>
	 * 例如："id:1,accountNo:12"
	 * @param sql
	 * @param kvPairs
	 */
	public void assertPropertyEqual(String sql,String kvPairs){
		Map<String,Object> qryMap = jdbcTemplate.queryForMap(sql);
		Assert.assertNotNull(qryMap);
		String[] propertyArray = kvPairs.split(",");
		for(String element : propertyArray){
			String[] elementArray = element.split(":");
			Assert.assertEquals(" column: "+elementArray[0]+" ,assert failure",elementArray[1], String.valueOf(qryMap.get(elementArray[0])));
		}
	}
}
