package org.tamacat.dao.util;

import static org.junit.Assert.*;

import java.util.ArrayList;

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
		assertEquals("{\"user_id\":\"guest\",\"password\":\"\",\"dept_id\":\"\",\"update_date\":\"\"}", json);
	}

	@Test
	public void testToStringArray() {
		ArrayList<User> list = new ArrayList<>();
		User bean = new User();
		bean.val(User.USER_ID, "guest");
		list.add(bean);
		
		String json = JSONUtils.toString(list, User.TABLE.columns());
		assertEquals("[{\"user_id\":\"guest\",\"password\":\"\",\"dept_id\":\"\",\"update_date\":\"\"}]", json);
	}
}
