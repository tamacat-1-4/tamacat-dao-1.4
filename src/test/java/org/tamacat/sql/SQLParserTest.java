/*
 * Copyright (c) 2007 TamaCat.org
 * All rights reserved.
 */
package org.tamacat.sql;

import org.junit.Before;
import org.junit.Test;
import org.tamacat.dao.Condition;
import org.tamacat.dao.Search.ValueConvertFilter;
import org.tamacat.dao.meta.DefaultTable;
import org.tamacat.dao.meta.Column;
import org.tamacat.dao.meta.Columns;
import org.tamacat.dao.meta.DataType;
import org.tamacat.sql.SQLParser;

import junit.framework.TestCase;

public class SQLParserTest extends TestCase {

	DefaultTable table1;
	Column column1;
	Column column2;
	Column column3;
	Column column4;
	SQLParser parser;
	
	@Before
	protected void setUp() throws Exception {
		column1 = Columns.create("name").type(DataType.STRING);
		
		column2 = Columns.create("id").type(DataType.NUMERIC);
		
		column3 = Columns.create("date").type(DataType.DATE);
		
		column4 = Columns.create("time").type(DataType.TIME);
		
		table1 = new DefaultTable("test1");
		table1.registerColumn(column1, column2, column3, column4);
		parser = new SQLParser();
	}

	static class TestValueConvertFilter implements ValueConvertFilter {
		public String convertValue(String value) {
			return value.replace("'", "''").replace("\\", "\\\\");
		}
	}
	
	@Test
	public void testParseValue() {
		assertEquals("'test'", parser.parseValue(column1, "test"));	
		assertEquals("123", parser.parseValue(column2, "123"));	
	}
	
	@Test
	public void testValue() {
		//System.out.println(parser.value(Condition.LIKE_PART, column1, "tama"));
		assertEquals("test1.name like '%tama%'", parser.value(column1, Condition.LIKE_PART, "tama"));
		
		assertEquals("test1.name=''", parser.value(column1, Condition.EQUAL, ""));
		assertEquals("test1.name=''", parser.value(column1, Condition.EQUAL, (String)null));
	}
	
	@Test
	public void testValueDate() {
		assertEquals("test1.date='2015-01-01'", parser.value(column3, Condition.EQUAL, "2015-01-01"));
		assertEquals("test1.date=null", parser.value(column3, Condition.EQUAL, ""));
	}
	
	@Test
	public void testValueTime() {
		assertEquals("test1.time='2015-01-01 00:00:00'", parser.value(column4, Condition.EQUAL, "2015-01-01 00:00:00"));
		assertEquals("test1.time=null", parser.value(column4, Condition.EQUAL, ""));
	}
	
	@Test
	public void testValues() {
		assertEquals("test1.name between 'a' and 'z'", parser.value(column1, Condition.BETWEEN, "a", "z"));
		assertEquals("test1.id between 100 and 200", parser.value(column2, Condition.BETWEEN, "100", "200"));
	}
	
	@Test
	public void testLikeStringValue() {
		assertEquals("test1.name like '%ta$_ma%' escape '$'", parser.value(column1, Condition.LIKE_PART, "ta_ma"));
		
		assertEquals("test1.name like '%$ta#_ma%' escape '#'", parser.value(column1, Condition.LIKE_PART, "$ta_ma"));
		
		assertEquals("test1.name like '%$%$_$%$_%' escape '$'", parser.value(column1, Condition.LIKE_PART, "%_%_"));
		
		assertEquals("test1.name like '%%'", parser.value(column1, Condition.LIKE_PART, ""));
	}
	
	@Test
	public void testInStringValue() {
		assertEquals("test1.name in ('abc','def','xyz')", parser.value(column1, Condition.IN, "abc", "def", "xyz"));
		
		assertEquals("test1.name in ('abc''','def','xyz')", parser.value(column1, Condition.IN, "abc'", "def", "xyz"));
	}
	
	@Test
	public void testInIntValue() {
		assertEquals("test1.id in (123)", parser.value(column2, Condition.IN, "123"));
		assertEquals("test1.id in (123,456,789)", parser.value(column2, Condition.IN, "123", "456", "789"));
	}
	
	//for debug.
	static public void assertEquals(String expected, String actual) {
		//System.out.println(actual);
		assertEquals(null, expected, actual);
	}
}
