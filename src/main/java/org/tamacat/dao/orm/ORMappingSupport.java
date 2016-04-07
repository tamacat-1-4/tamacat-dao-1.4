/*
 * Copyright (c) 2008 tamacat.org
 * All rights reserved.
 */
package org.tamacat.dao.orm;

import java.util.Map;

import org.tamacat.dao.meta.Column;

public interface ORMappingSupport extends Map<String, Object> {

	ORMappingSupport mapping(Object name, Object value);

	String getValue(Column column);

	ORMappingSupport setValue(Column column, String value);

	boolean isUpdate(Object name);
}
