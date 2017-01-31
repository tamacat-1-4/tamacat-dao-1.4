package org.tamacat.dao.util;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.tamacat.dao.test.Data;

public class MappingUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMapping() {
	}

	@Test
	public void testParseDate() {
 		long time = 1500000000000L;
		assertEquals(new Date(time), MappingUtils.parseDate(time));
		
		assertEquals(new Date(time), MappingUtils.parseDate("2017-07-14 11:40:00.000"));
		assertEquals(new Date(time), MappingUtils.parseDate("2017-07-14 11:40:00"));
		assertEquals(new Date(1499958000000L), MappingUtils.parseDate("2017-07-14"));
	}

	@Test
	public void testParse() {
 		long time = 1500000000000L;
		Data data = new Data();
		data.val(Data.UPDATE_DATE, new Date(time));
		System.out.println(MappingUtils.parseString(Data.UPDATE_DATE, new Date(time).getTime()));
		
	}

	@Test
	public void testGetColumnName() {
	}

	@Test
	public void testValues() {
	}

}
