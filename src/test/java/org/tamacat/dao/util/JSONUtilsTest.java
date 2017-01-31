package org.tamacat.dao.util;

import static org.junit.Assert.*;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;

import javax.json.Json;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tamacat.dao.test.User;

public class JSONUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testToString() {
		User bean = new User();
		bean.val(User.USER_ID, "guest");		
		String json = JSONUtils.toString(bean, User.TABLE.columns());
		assertEquals("{\"user_id\":\"guest\",\"password\":\"\",\"dept_id\":\"\",\"update_date\":\"\",\"age\":\"\"}", json);
	}

	@Test
	public void testToStringArray() {
		ArrayList<User> list = new ArrayList<>();
		User bean = new User();
		bean.val(User.USER_ID, "guest");
		list.add(bean);
		
		String json = JSONUtils.toString(list, User.TABLE.columns());
		assertEquals("[{\"user_id\":\"guest\",\"password\":\"\",\"dept_id\":\"\",\"update_date\":\"\",\"age\":\"\"}]", json);
	}
	
	@Test
	public void testParse() {
		String json = "[{\"user_id\":\"guest\",\"password\":\"PASSWORD\",\"dept_id\":\"TEST DEPT\",\"update_date\":\"2017-01-01 01:23:45\",\"age\":\"20\"}]";
		User bean = new User();
		User.UPDATE_DATE.format(null);
		JSONUtils.parse(bean, Json.createParser(new StringReader(json)), User.TABLE.columns());
		System.out.println(bean.toJson(User.TABLE.columns()).build().toString());
		assertEquals(bean.val(User.USER_ID), "guest");
		assertEquals(bean.val(User.PASSWORD), "PASSWORD");
		assertEquals(bean.val(User.DEPT_ID), "TEST DEPT");
		assertEquals(bean.val(User.AGE), "20");
	}
	
	@Test
	public void testParse_TIME_msec() {
		String json = "[{\"user_id\":\"guest\",\"password\":\"PASSWORD\",\"dept_id\":\"TEST DEPT\",\"update_date\":\"2017-01-01 01:23:45.678\",\"age\":\"20\"}]";
		User bean = new User();
		User.UPDATE_DATE.format("yyyy-MM-dd HH:mm:ss.SSS");
		JSONUtils.parse(bean, Json.createParser(new StringReader(json)), User.TABLE.columns());
		//{"user_id":"guest","password":"PASSWORD","dept_id":"TEST DEPT","update_date":1483201425000,"age":20}
		System.out.println(bean.toJson(User.TABLE.columns()).build().toString());

		assertEquals(bean.val(User.USER_ID), "guest");
		assertEquals(bean.val(User.PASSWORD), "PASSWORD");
		assertEquals(bean.val(User.DEPT_ID), "TEST DEPT");
		assertEquals(bean.val(User.AGE), "20");
		User.UPDATE_DATE.format(null);
	}
	
	@Test
	public void testParse_DATE() {
		User bean = new User();
		
		bean.val(User.UPDATE_DATE, new Date(1500000000000L));
		String json = bean.toJson(User.UPDATE_DATE).build().toString();
		assertEquals("{\"update_date\":1500000000000}", json);
		System.out.println(bean.val(User.UPDATE_DATE));
		
		//{"update_date":1500000000000}
		
		User.parse(bean); //TODO rename
		
		User.UPDATE_DATE.format("yyyy-MM-dd HH:mm:ss.SSS");
		System.out.println(bean.toJson(User.UPDATE_DATE).build().toString());
		//{"update_date":"2017-07-14 11:40:00.000"}
		
		User.UPDATE_DATE.format(null);
	}
}
