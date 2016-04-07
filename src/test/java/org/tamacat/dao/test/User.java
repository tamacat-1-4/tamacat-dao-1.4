/*
 * Copyright (c) 2008 TamaCat.org
 * All rights reserved.
 */
package org.tamacat.dao.test;

import org.tamacat.dao.meta.DefaultColumn;
import org.tamacat.dao.meta.DefaultTable;
import org.tamacat.dao.meta.DataType;
import org.tamacat.dao.orm.MapBasedORMappingBean;

public class User extends MapBasedORMappingBean {

	private static final long serialVersionUID = 1L;

	public static final DefaultTable TABLE = new DefaultTable("users");
	public static final DefaultColumn USER_ID = new DefaultColumn("user_id");
	public static final DefaultColumn PASSWORD = new DefaultColumn("password");
	public static final DefaultColumn DEPT_ID = new DefaultColumn("dept_id");
	public static final DefaultColumn UPDATE_DATE = new DefaultColumn("update_date");

	static {
		USER_ID.type(DataType.STRING).setPrimaryKey(true);
		PASSWORD.type(DataType.STRING);
		DEPT_ID.type(DataType.STRING);
		UPDATE_DATE.type(DataType.TIME);
		TABLE.registerColumn(USER_ID, PASSWORD, DEPT_ID, UPDATE_DATE);
	}
}
