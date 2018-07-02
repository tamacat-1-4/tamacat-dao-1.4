package org.tamacat.dao.meta;

import static org.junit.Assert.*;

import org.junit.Test;

public class ColumnsTest {

	@Test
	public void testCreate() {
		assertEquals(DataType.STRING, Columns.create("test_data").getType());
		assertEquals(DataType.TIME, Columns.create("create_time").getType());
		assertEquals(DataType.TIME, Columns.create("update_date").getType());	
	}
}
